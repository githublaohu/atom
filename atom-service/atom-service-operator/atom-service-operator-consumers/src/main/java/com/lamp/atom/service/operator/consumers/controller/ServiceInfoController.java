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

import com.lamp.atom.service.operator.consumers.utils.ResultObjectEnums;
import com.lamp.atom.service.operator.entity.ServiceInfoEntity;
import com.lamp.atom.service.operator.service.ServiceInfoService;
import com.lamp.decoration.core.result.ResultObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequestMapping("/serviceInfo")
@RestController("serviceInfoController")
@Api(tags = {"服务配置操作接口"})
public class ServiceInfoController {

    @Reference
    private ServiceInfoService serviceInfoService;

    /**
     * 添加服务配置
     *
     * @param serviceInfoEntity
     */
    @PostMapping("/insertServiceInfo")
    @ApiOperation(value = "添加服务配置")
    public ResultObject<String> insertServiceInfo(@RequestBody ServiceInfoEntity serviceInfoEntity) {
        // 字段判空
        if (Objects.isNull(serviceInfoEntity.getSpaceId())) {
            log.info("参数校验失败 {}", serviceInfoEntity);
            return ResultObjectEnums.CHECK_PARAMETERS_FAIL.getResultObject();
        }
        try {
            serviceInfoService.insertServiceInfoEntity(serviceInfoEntity);
        } catch (Exception e) {
            log.warn("服务配置插入失败 {}", e);
            return ResultObjectEnums.FAIL.getResultObject();
        }
        return ResultObjectEnums.SUCCESS.getResultObject();
    }

    /**
     * 修改服务配置
     *
     * @param serviceInfoEntity
     * @return
     */
    @PostMapping("/updateServiceInfo")
    @ApiOperation(value = "修改服务配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",dataTypeClass = java.lang.Long.class,paramType="body" ,dataType = "Long"),
            @ApiImplicitParam(name="deleteFlag",dataTypeClass = java.lang.Long.class,paramType="body" ,dataType = "Long")
    })
    public ResultObject<String> updateServiceInfo(@ApiIgnore @RequestBody ServiceInfoEntity serviceInfoEntity) {
        try {
            serviceInfoService.updateServiceInfoEntity(serviceInfoEntity);
        } catch (Exception e) {
            log.warn("服务配置修改失败 {}", e);
            return ResultObjectEnums.FAIL.getResultObject();
        }
        return ResultObjectEnums.SUCCESS.getResultObject();
    }

    /**
     * 模糊查询多个服务配置
     *
     * @param params
     * @return
     */
    @PostMapping("/queryServiceInfosByKeyword")
    @ApiOperation(value = "模糊查询多个服务配置")
    public List<ServiceInfoEntity> queryServiceInfosByKeyword(@RequestBody HashMap<String, String> params) {
        try {
            return serviceInfoService.queryServiceInfoEntitysByKeyword(params.get("keyword"));
        } catch (Exception e) {
            log.warn("服务配置查询失败 {}", e);
            return null;
        }
    }

    /**
     * 查询多个服务配置
     *
     * @param serviceInfoEntity
     * @return
     */
    @PostMapping("/queryServiceInfos")
    @ApiOperation(value = "查询多个服务配置")
    public List<ServiceInfoEntity> queryServiceInfos(@RequestBody(required = false) ServiceInfoEntity serviceInfoEntity) {
        try {
            return serviceInfoService.queryServiceInfoEntitys(serviceInfoEntity);
        } catch (Exception e) {
            log.warn("服务配置查询失败 {}", e);
            return null;
        }
    }

    /**
     * 查询单个服务配置
     *
     * @param serviceInfoEntity
     * @return
     */
    @PostMapping("/queryServiceInfo")
    @ApiOperation(value = "查询单个服务配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", paramType = "body" ,dataType = "Long", dataTypeClass = java.lang.Long.class, defaultValue = "1")
    })
    public ServiceInfoEntity queryServiceInfo(@ApiIgnore @RequestBody ServiceInfoEntity serviceInfoEntity) {
        try {
            ServiceInfoEntity serviceInfoEntity1 = serviceInfoService.queryServiceInfoEntity(serviceInfoEntity);
            return serviceInfoEntity1;
        } catch (Exception e) {
            log.warn("服务配置查询失败 {}", e);
            return null;
        }
    }
}
