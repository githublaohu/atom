package com.lamp.atom.service.operator.provider.service.impl;

import java.util.List;

import org.apache.dubbo.config.annotation.Service;

import com.lamp.atom.service.operator.entity.ServiceInfo;
import com.lamp.atom.service.operator.provider.mapper.ServiceInfoMapper;
import com.lamp.atom.service.operator.service.ServiceInfoService;

@Service
public class ServiceInfoServiceImpl implements ServiceInfoService{

	private ServiceInfoMapper serviceInfoMapper;
	
	@Override
	public void insertServiceInfo(ServiceInfo serviceInfo) {
		serviceInfoMapper.insertServiceInfo(serviceInfo);
		
	}

	@Override
	public Integer updateServiceInfoStatus(ServiceInfo serviceInfo) {
		return serviceInfoMapper.updateServiceInfoStatus(serviceInfo);
	}

	@Override
	public List<ServiceInfo> queryServiceInfoList(ServiceInfo serviceInfo) {
		return serviceInfoMapper.queryServiceInfoList(serviceInfo);
	}

}
