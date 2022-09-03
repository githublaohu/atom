/*
 *Copyright (c) [Year] [name of copyright holder]
 *[Software Name] is licensed under Mulan PubL v2.
 *You can use this software according to the terms and conditions of the Mulan PubL v2.
 *You may obtain a copy of Mulan PubL v2 at:
 *         http://license.coscl.org.cn/MulanPubL-2.0
 *THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 *EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 *MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 *See the Mulan PubL v2 for more details.
 */
package com.lamp.atom.service.operator.consumers.function;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.lamp.atom.schedule.api.Schedule;
import com.lamp.atom.schedule.core.AtomScheduleService;
import com.lamp.atom.service.operator.entity.RuntimeEntity;
import com.lamp.atom.service.operator.service.RuntimeService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PortCreatingFunction {

    @Reference
    private RuntimeService runtimeService;

    @Value("${nacos.config.server-addr}")
    private String nacosAddr;
    @Value("${nacos.config.namespace}")
    private String namespace;
    @Value("${server.port}")
    private String port;

    private String ip = getIp();

    @Autowired
    private AtomScheduleService atomScheduleService;

    public String getIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.info(e.getMessage(), e);
            return null;
        }
    }

    volatile Map<String, ServiceAndPort> serviceAndPortMap = new HashMap<>();

    NamingService namingService = null;

    /**
     * 获取nacos实例
     *
     * @throws NacosException
     */
//    @PostConstruct
    public void init() throws NacosException {
        Properties properties = new Properties();
        properties.put("serverAddr", this.nacosAddr);
        properties.put("namespace", this.namespace);
        namingService = NamingFactory.createNamingService(properties);

        this.listen();

        NacosRuntime nacosRuntime = new NacosRuntime();
        nacosRuntime.run();
    }

    /**
     * 刷新session服务和standalone服务实例
     *
     * @throws NacosException
     */
    public void listen() throws NacosException {
        Timer timer = new Timer("serviceListener", true);
        timer.schedule(new TimerTask() {
            @SneakyThrows
            @Override
            public void run() {
                PortCreatingFunction.this.registerService();

                // 监听session和standalone服务，更新ip&port
                List<String> serviceNames = new ArrayList<>();
                serviceNames.add("atom-runtime-python-service-session");
                serviceNames.add("atom-runtime-python-service-standalone");
                Map<String, ServiceAndPort> refreshServiceAndPortMap = new HashMap<>();

                for (String serviceName : serviceNames) {
                    List<Instance> allInstances = namingService.getAllInstances(serviceName);
                    for (Instance instance : allInstances) {
                        ServiceAndPort serviceAndPort = new ServiceAndPort();
                        serviceAndPort.ip = instance.getIp();
                        serviceAndPort.port = new AtomicInteger(instance.getPort());
                        ConcurrentHashMap<Integer, Integer> concurrentHashMap = new ConcurrentHashMap<>();
                        concurrentHashMap.put(instance.getPort(), instance.getPort());
                        serviceAndPort.portMap = concurrentHashMap;
                        refreshServiceAndPortMap.put(instance.getIp(), serviceAndPort);
                    }
                }

                serviceAndPortMap = refreshServiceAndPortMap;
            }
        }, 100, 5000);
    }

    /**
     * 获取对应ip的可用端口
     * 1、每次调用后该ip的端口自增一
     * 2、由创建operator时调用k8s前调用到
     *
     * @param ip
     * @return
     */
    public Integer getPort(String ip) {
        PortCreatingFunction.ServiceAndPort serviceAndPort = this.serviceAndPortMap.get(ip);
        for (; ; ) {
            if (Objects.isNull(serviceAndPort)) {
                serviceAndPort = new PortCreatingFunction.ServiceAndPort();
                serviceAndPort.ip = ip;
                serviceAndPort.port = new AtomicInteger(20000);
                serviceAndPort.portMap = new ConcurrentHashMap<>();
            }
            Integer port = serviceAndPort.port.incrementAndGet() % 50000 + 10000;
            Integer myValue = serviceAndPort.portMap.computeIfAbsent(port, value -> port);
            if (Objects.nonNull(myValue)) {
                return port;
            }
        }
    }

    /**
     * 注册实例到服务中
     *
     * @throws NacosException
     */
    public void registerService() throws NacosException {
        namingService.registerInstance("atom-service-operator", ip, Integer.parseInt(port));
    }

    public static class ServiceAndPort {
        String ip;
        private AtomicInteger port = new AtomicInteger();
        private ConcurrentHashMap<Integer, Integer> portMap = new ConcurrentHashMap<>();
    }

    class NacosRuntime implements Runnable {

        private Set<String> currentService = new HashSet<>();

        private Set<String> newestService = new HashSet<>();

        private Map<String, List<Instance>> currentInstances = new HashMap<>();

        @Override
        public void run() {
            while (true) {
                try {
                    while (true) {
                        Thread.sleep(1000);
                        this.getServices();
                        if (Objects.isNull(newestService) || newestService.isEmpty()) {
                            continue;
                        }
                        // 已经删除的服务
                        Set<String> newCurrentService = new HashSet<>(currentService);
                        newCurrentService.removeAll(newestService);
                        for (String service : newCurrentService) {
                            log.info("service is unregister(服务已下线):" + service);
                        }
                        currentService = newestService;
                        for (String serviceName : newestService) {
                            List<Instance> newestInstance = namingService.selectInstances(serviceName, true);
                            List<Instance> currentInstance = currentInstances.get(serviceName);
                            if (Objects.isNull(currentInstance)) {
                                for (Instance instance : newestInstance) {
                                    this.register(instance);
                                }
                            } else {
                                this.classification(currentInstance, newestInstance);
                            }
                            currentInstances.put(serviceName, newestInstance);
                        }

                    }
                } catch (Throwable e) {
                    log.error(e.getMessage(), e);
                }
            }

        }

        private void register(Instance instance) {
            RuntimeEntity runtimeEntity = new RuntimeEntity();
            try {
                PortCreatingFunction.this.runtimeService.updateRuntimeStatus(runtimeEntity);
            } catch (Exception e) {
                log.error("instance info : {}", instance);
                log.error(e.getMessage(), e);
            }
        }

        private void unRegister(Instance instance) {
            RuntimeEntity runtimeEntity = new RuntimeEntity();
            Schedule schedule = new Schedule();
            try {
                Integer code = PortCreatingFunction.this.runtimeService.updateRuntimeStatus(runtimeEntity);
                if (code == 0) {
                    return;
                }
                PortCreatingFunction.this.atomScheduleService.uninstallOperators(schedule);
                PortCreatingFunction.this.runtimeService.updateRuntimeStatus(runtimeEntity);
            } catch (Exception e) {
                log.error("instance info : {}", instance);
                log.error("runtimeEntity info : {}", runtimeEntity);
                log.error("schedule info : {}", schedule);
                log.error(e.getMessage(), e);
            }
        }

        private void classification(List<Instance> currentInstance, List<Instance> newestInstance) {
            Map<String, Instance> oldHostMap = new HashMap<String, Instance>(currentInstance.size());
            for (Instance host : currentInstance) {
                oldHostMap.put(host.toInetAddr(), host);
            }
            Map<String, Instance> newHostMap = new HashMap<String, Instance>(newestInstance.size());
            for (Instance host : newestInstance) {
                newHostMap.put(host.toInetAddr(), host);
            }
            List<Map.Entry<String, Instance>> newServiceHosts = new ArrayList<Map.Entry<String, Instance>>(newHostMap.entrySet());
            for (Map.Entry<String, Instance> entry : newServiceHosts) {
                Instance host = entry.getValue();
                String key = entry.getKey();
                if (oldHostMap.containsKey(key) && !StringUtils.equals(host.toString(), oldHostMap.get(key).toString())) {
                    this.register(host);
                    continue;
                }

                if (!oldHostMap.containsKey(key)) {
                    this.register(host);
                }
            }
            for (Map.Entry<String, Instance> entry : oldHostMap.entrySet()) {
                Instance host = entry.getValue();
                String key = entry.getKey();
                if (newHostMap.containsKey(key)) {
                    continue;
                }
                if (!newHostMap.containsKey(key)) {
                    this.unRegister(host);
                }
            }
        }

        void getServices() throws NacosException {
            newestService.add("atom-runtime-python-service-session");
            newestService.add("atom-runtime-python-service-standalone");
        }
    }
}
