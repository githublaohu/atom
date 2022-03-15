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
package com.lamp.atom.service.operator.provider.service.impl;

import com.lamp.atom.service.operator.entity.ServiceInfoEntity;
import com.lamp.atom.service.operator.provider.mapper.ServiceInfoMapper;
import com.lamp.atom.service.operator.service.ServiceInfoService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ServiceInfoServiceImpl implements ServiceInfoService {

    @Autowired
    private ServiceInfoMapper serviceInfoMapper;

    @Override
    public Integer createServiceInfoEntity(ServiceInfoEntity serviceInfoEntity) {
        return null;
    }

    @Override
    public Integer insertServiceInfoEntity(ServiceInfoEntity serviceInfoEntity) {
        return serviceInfoMapper.insertServiceInfoEntity(serviceInfoEntity);
    }

    @Override
    public List<ServiceInfoEntity> queryServiceInfoEntitysByKeyword(String keyword) {
        return serviceInfoMapper.queryServiceInfoEntitysByKeyword(keyword);
    }

    @Override
    public Integer updateServiceInfoEntity(ServiceInfoEntity serviceInfoEntity) {
        return serviceInfoMapper.updateServiceInfoEntity(serviceInfoEntity);
    }

    @Override
    public List<ServiceInfoEntity> queryServiceInfoEntitys(ServiceInfoEntity serviceInfoEntity) {
        return serviceInfoMapper.queryServiceInfoEntitys(serviceInfoEntity);
    }

    @Override
    public ServiceInfoEntity queryServiceInfoEntity(ServiceInfoEntity serviceInfoEntity) {
        return serviceInfoMapper.queryServiceInfoEntity(serviceInfoEntity);
    }
}
