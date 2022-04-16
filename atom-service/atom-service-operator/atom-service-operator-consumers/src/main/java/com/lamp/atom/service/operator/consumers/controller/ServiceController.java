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
package com.lamp.atom.service.operator.consumers.controller;

import java.util.Date;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lamp.atom.schedule.api.Schedule;
import com.lamp.atom.schedule.core.AtomScheduleService;
import com.lamp.atom.service.domain.CaseSourceType;
import com.lamp.atom.service.domain.OperatorRuntimeStatus;
import com.lamp.atom.service.operator.consumers.function.PortCreatingFunction;
import com.lamp.atom.service.operator.entity.RuntimeEntity;
import com.lamp.atom.service.operator.entity.ServiceInfoEntity;
import com.lamp.atom.service.operator.service.RuntimeService;
import com.lamp.atom.service.operator.service.ServiceInfoService;

import io.swagger.annotations.Api;

/**
 * 推理
 * 
 * @author laohu
 *
 */
@RequestMapping("/service")
@RestController("serviceController")
@Api(hidden = true)
public class ServiceController {

    @Autowired
    private PortCreatingFunction portCreatingFunction;
    
    @Autowired(required = false)
    private AtomScheduleService atomScheduleService;
    
    @Reference
    private ServiceInfoService serviceInfoService;
    
    @Reference
    private RuntimeService runtimeService;
    

    @PostMapping("/getServicePort")
    public Long getServicePort(@RequestBody String ip) {
        return portCreatingFunction.getPort(ip).longValue();
    }
    
    @RequestMapping("/createRuntime")
    public Integer createRuntime(RuntimeEntity runtimeEntity) {
    	// 查询服务状态
    	ServiceInfoEntity serviceInfoEntity = new ServiceInfoEntity();
    	serviceInfoEntity.setId(runtimeEntity.getSourceId());
    	serviceInfoEntity = serviceInfoService.queryServiceInfoEntity(serviceInfoEntity);
    	serviceInfoEntity.setSiName(serviceInfoEntity.getSiName());
    	// 创建runtime 对象 主类型
    	
    	runtimeEntity.setSpaceId(serviceInfoEntity.getSpaceId());
    	runtimeEntity.setCaseSourceType(CaseSourceType.SERVICE);
    	runtimeEntity.setSourceId(serviceInfoEntity.getId());
    	
    	runtimeEntity.setServerIp("main");
    	runtimeEntity.setServerPort("0");
    	runtimeEntity.setStartTime(new Date());
    	runtimeEntity.setEndTime(new Date());
    	runtimeEntity.setEstimateStartTime(new Date());
    	runtimeEntity.setEstimateEndTime(new Date());
    	runtimeEntity.setOperatorRuntimeStatus(OperatorRuntimeStatus.QUEUING);

		//todo runtime的启动/关闭人需要用户管理模块的字段
    	runtimeEntity.setStartId(1L);
    	runtimeEntity.setStartName("");
    	runtimeEntity.setEndId(1L);
    	runtimeEntity.setEndName("");
    	
    	
    	runtimeService.insertRuntimeEntity(runtimeEntity);
    	
    	
    	
    	// 如果是停止，那么从新创建一个
    	
    	// 如果是等待运行，
    	Schedule schedule = new Schedule();
    	
    	schedule.setNodeId(runtimeEntity.getId());
    	schedule.setNodeName(serviceInfoEntity.getSiName());
    	atomScheduleService.createService(schedule);
    	// 修改name 与 状态
    	return 0;
    }
    
    @RequestMapping("/dropRuntime")
    public Integer dropRuntime(RuntimeEntity serviceInfo) {
    	Schedule schedule = new Schedule();
    	// 
    	atomScheduleService.closeService(schedule);
    	return 0;
    }

}
