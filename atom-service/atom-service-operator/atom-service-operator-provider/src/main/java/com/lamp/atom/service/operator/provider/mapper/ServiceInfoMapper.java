package com.lamp.atom.service.operator.provider.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.lamp.atom.service.operator.entity.ServiceInfo;

@Mapper
public interface ServiceInfoMapper {

	
	@Insert("")
	public void insertServiceInfo(ServiceInfo serviceInfo);
	
	public Integer updateServiceInfoStatus(ServiceInfo serviceInfo);
	
	@Select("select * from service_info where space_id = #{spaceId} order by si_runtime_start_time desc")
	public List<ServiceInfo> queryServiceInfoList(ServiceInfo serviceInfo);
	
}
