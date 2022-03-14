package com.lamp.atom.service.operator.provider.service.impl;

import java.util.List;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.lamp.atom.service.operator.entity.ServiceInfo;
import com.lamp.atom.service.operator.entity.ServiceInfoEntity;
import com.lamp.atom.service.operator.provider.mapper.ServiceInfoMapper;
import com.lamp.atom.service.operator.service.ServiceInfoService;

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
    public List<ServiceInfoEntity> queryServiceInfoEntitys() {
        return serviceInfoMapper.queryServiceInfoEntitys();
    }

    @Override
    public ServiceInfoEntity queryServiceInfoEntity(ServiceInfoEntity serviceInfoEntity) {
        return serviceInfoMapper.queryServiceInfoEntity(serviceInfoEntity);
    }
}
