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
package com.lamp.atom.service.sdk.domain;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.lamp.atom.service.sdk.sdk.AtomReasonService;
import com.lamp.light.Light;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

public class AtomService {

    private AtomConfig atomConfig;

    private NamingService namingService;

    private Map<Long, List<Instance>> sceneIdByInstances = new ConcurrentHashMap<>();

    public AtomService(AtomConfig atomConfig) {
        this.atomConfig = atomConfig;
    }

    public void init() throws Exception {
        this.namingService = NamingFactory.createNamingService(this.atomConfig.getNacosAddr());
    }

    public AtomReasonService getAtomReasonService() throws Exception {
        Light light = Light.Builder().build();
        return light.create(AtomReasonService.class);
    }

    public List<Instance> getInstance(ReasonRequest reasonRequest) throws NacosException {
        Long sceneId = reasonRequest.getSceneId();
        List<Instance> instanceList = this.sceneIdByInstances.get(sceneId);
        if (instanceList == null) {
            instanceList = this.namingService.getAllInstances(atomConfig.getNacosNamespace());
            this.sceneIdByInstances.put(sceneId, instanceList);
            /*this.namingService.subscribe(atomConfig.getNacosNamespace(), new EventListener() {
                @Override
                public void onEvent(Event event) {

                }
            });*/
        }
        //负载均衡
        ThreadLocalRandom.current().nextInt();
        return instanceList;
    }
}
