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
package com.lamp.atom.service.space.consumers.controller;

import com.lamp.atom.service.space.consumers.utils.ResultObjectEnums;
import com.lamp.atom.service.space.entity.EnvironmentEntity;
import com.lamp.atom.service.space.service.EnvironmentService;
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

@Slf4j
@RequestMapping("/environment")
@RestController("environmentController")
@Api(tags = {"环境操作接口"})
public class EnvironmentController {

    @Reference
    private EnvironmentService environmentService;

    /**
     * 添加环境
     *
     * @param environmentEntity
     */
    @PostMapping("/insertEnvironment")
    @ApiOperation(value = "添加环境")
    public ResultObject<String> insertEnvironment(@RequestBody EnvironmentEntity environmentEntity) {
        try {
            environmentService.insertEnvironmentEntity(environmentEntity);
        } catch (Exception e) {
            log.warn("环境插入失败 {}", e);
            return ResultObjectEnums.FAIL.getResultObject();
        }
        return ResultObjectEnums.SUCCESS.getResultObject();
    }

    /**
     * 修改环境
     *
     * @param environmentEntity
     * @return
     */
    @PostMapping("/updateEnvironment")
    @ApiOperation(value = "修改环境")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",dataTypeClass = Long.class,paramType="body" ,dataType = "Long"),
            @ApiImplicitParam(name="deleteFlag",dataTypeClass = Long.class,paramType="body" ,dataType = "Long")
    })
    public ResultObject<String> updateEnvironment(@ApiIgnore @RequestBody EnvironmentEntity environmentEntity) {
        try {
            environmentService.updateEnvironmentEntity(environmentEntity);
        } catch (Exception e) {
            log.warn("环境修改失败 {}", e);
            return ResultObjectEnums.FAIL.getResultObject();
        }
        return ResultObjectEnums.SUCCESS.getResultObject();
    }

    /**
     * 模糊查询多个环境
     *
     * @param params
     * @return
     */
    @PostMapping("/queryEnvironmentsByKeyword")
    @ApiOperation(value = "模糊查询多个环境")
    public List<EnvironmentEntity> queryEnvironmentsByKeyword(@RequestBody HashMap<String, String> params) {
        try {
            return environmentService.queryEnvironmentEntitysByKeyword(params.get("keyword"));
        } catch (Exception e) {
            log.warn("环境查询失败 {}", e);
            return null;
        }
    }

    /**
     * 查询多个环境
     *
     * @param environmentEntity
     * @return
     */
    @PostMapping("/queryEnvironments")
    @ApiOperation(value = "查询多个环境")
    public List<EnvironmentEntity> queryEnvironments(@RequestBody EnvironmentEntity environmentEntity) {
        try {
            return environmentService.queryEnvironmentEntitys(environmentEntity);
        } catch (Exception e) {
            log.warn("环境查询失败 {}", e);
            return null;
        }
    }

    /**
     * 查询单个环境
     *
     * @param environmentEntity
     * @return
     */
    @PostMapping("/queryEnvironment")
    @ApiOperation(value = "查询单个环境")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", paramType = "body" ,dataType = "Long", dataTypeClass = Long.class, defaultValue = "1")
    })
    public EnvironmentEntity queryEnvironment(@ApiIgnore @RequestBody EnvironmentEntity environmentEntity) {
        try {
            return environmentService.queryEnvironmentEntity(environmentEntity);
        } catch (Exception e) {
            log.warn("环境查询失败 {}", e);
            return null;
        }
    }
}
