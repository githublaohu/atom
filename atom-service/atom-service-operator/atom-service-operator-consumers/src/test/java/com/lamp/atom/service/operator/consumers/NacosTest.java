package com.lamp.atom.service.operator.consumers;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import org.junit.Test;

import java.util.*;

public class NacosTest {

    NamingService namingService = null;

    public void init() throws NacosException {
        Properties properties = new Properties();
        properties.put("serverAddr", "124.223.198.143:8848");
        properties.put("namespace", "atom-dev");
        namingService = NamingFactory.createNamingService(properties);
    }


    @Test
    public void serviceTest() throws NacosException {
        this.init();

        List<String> serviceNames = new ArrayList<>();
        serviceNames.add("atom-runtime-python-service-standalone");
//        serviceNames.add("atom-service-operator");

        for (String serviceName : serviceNames) {
            List<Instance> allInstances = namingService.getAllInstances(serviceName);
            for (Instance instance : allInstances) {
                String ip = instance.getIp();
                int port = instance.getPort();
            }
        }
    }
}
