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

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
public class PortCreatingFunction {

    @Value("${nacos.config.server-addr}")
    private String nacosAddr;
    @Value("${nacos.config.namespace}")
    private String namespace;
    @Value("${server.port}")
    private String port;

    private String ip = getIp();

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

    //获取nacos实例
    @PostConstruct
    public void init() throws NacosException {
        Properties properties = new Properties();
        properties.put("serverAddr", this.nacosAddr);
        properties.put("namespace", this.namespace);
        namingService = NamingFactory.createNamingService(properties);
        this.listen();
    }

    //监听session服务和standalone服务
    public void listen() throws NacosException {
        Timer timer = new Timer("serviceListener", true);
        timer.schedule(new TimerTask() {
            @SneakyThrows
            @Override
            public void run() {
                PortCreatingFunction.this.registerService();
                List<String> serviceNames = new ArrayList<>();
                serviceNames.add("session");
                serviceNames.add("standalone");
                Map<String, ServiceAndPort> newServiceAndPortMap = new HashMap<>();

                for (String serviceName : serviceNames) {
                    List<Instance> allInstances = namingService.getAllInstances(serviceName);
                    for (Instance instance : allInstances) {
                        ServiceAndPort serviceAndPort = new ServiceAndPort();
                        serviceAndPort.ip = instance.getIp();
                        serviceAndPort.port = new AtomicInteger(instance.getPort());
                        ConcurrentHashMap<Integer, Integer> concurrentHashMap = new ConcurrentHashMap<>();
                        concurrentHashMap.put(instance.getPort(), instance.getPort());
                        serviceAndPort.portMap = concurrentHashMap;
                        newServiceAndPortMap.put(instance.getIp(), serviceAndPort);
                    }
                }

                serviceAndPortMap = newServiceAndPortMap;
            }
        }, 100, 5000);
    }

    //获取可用端口
    public Integer getPort(String ip) {
        ServiceAndPort serviceAndPort = this.serviceAndPortMap.get(ip);
        for (; ; ) {
            if (Objects.isNull(serviceAndPort)) {
                serviceAndPort = new ServiceAndPort();
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

    //注册实例到服务中
    public void registerService() throws NacosException {
        namingService.registerInstance("atom-service-operator", ip, Integer.parseInt(port));
    }

    public static class ServiceAndPort {
        private String ip;
        private AtomicInteger port = new AtomicInteger();
        private ConcurrentHashMap<Integer, Integer> portMap = new ConcurrentHashMap<>();
    }
}


