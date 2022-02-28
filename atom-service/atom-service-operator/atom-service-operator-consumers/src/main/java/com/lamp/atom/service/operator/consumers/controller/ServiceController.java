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

import com.lamp.atom.service.operator.consumers.function.PortCreatingFunction;
import com.lamp.atom.service.operator.entity.ServiceInfo;
import com.lamp.atom.service.operator.service.ServiceInfoService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/service")
@RestController("serviceController")
public class ServiceController {

    @Autowired
    private PortCreatingFunction portCreatingFunction;
    
    private ServiceInfoService serviceInfoService;

    @RequestMapping("/getServicePort")
    public Integer getServicePort(@RequestBody String ip) {
        return portCreatingFunction.getPort(ip);
    }
    
    @RequestMapping("/createRuntime")
    public Integer createRuntime(ServiceInfo serviceInfo) {
    	// 查询服务状态
    	
    	// 如果服务在运行中，那么退出
    	
    	// 如果是停止，那么从新创建一个
    	
    	// 如果是等待运行，
    	return 0;
    }
    
    @RequestMapping("/dropRuntime")
    public Integer dropRuntime() {
    	
    	return 0;
    }

}
