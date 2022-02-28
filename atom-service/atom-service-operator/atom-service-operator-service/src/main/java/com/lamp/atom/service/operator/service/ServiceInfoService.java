package com.lamp.atom.service.operator.service;

import java.util.List;

import com.lamp.atom.service.operator.entity.ServiceInfo;

public interface ServiceInfoService {

	public void insertServiceInfo(ServiceInfo serviceInfo);
	
	public Integer updateServiceInfoStatus(ServiceInfo serviceInfo);
	
	public List<ServiceInfo> queryServiceInfoList(ServiceInfo serviceInfo);
}
