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


import com.lamp.atom.service.operator.entity.RuntimeEntity;
import com.lamp.atom.service.operator.service.RuntimeService;
import com.lamp.atom.service.operator.consumers.utils.ResultObjectEnums;
import com.lamp.decoration.core.result.ResultObject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@Slf4j
@RequestMapping("/runtime")
@RestController("runtimeController")
@Api(tags = {"运行实例操作接口"})
public class RuntimeController {

    @Reference
    private RuntimeService runtimeService;

    /**
     * 添加实例
     *
     * @param runtimeEntity
     */
    @PostMapping("/insertRuntime")
    @ApiOperation(value = "添加实例")
    public ResultObject<String> insertRuntime(@RequestBody RuntimeEntity runtimeEntity) {
        try {
            runtimeService.insertRuntimeEntity(runtimeEntity);
        } catch (Exception e) {
            log.warn("实例插入失败 {}", e);
            return ResultObjectEnums.FAIL.getResultObject();
        }
        return ResultObjectEnums.SUCCESS.getResultObject();
    }

    /**
     * 修改实例
     *
     * @param runtimeEntity
     * @return
     */
    @PostMapping("/updateRuntime")
    @ApiOperation(value = "修改实例")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataTypeClass = java.lang.Long.class, paramType = "body", dataType = "Long"),
            @ApiImplicitParam(name = "deleteFlag", dataTypeClass = java.lang.Long.class, paramType = "body", dataType = "Long")
    })
    public ResultObject<String> updateRuntime(@ApiIgnore @RequestBody RuntimeEntity runtimeEntity) {
        try {
            runtimeService.updateRuntimeEntity(runtimeEntity);
        } catch (Exception e) {
            log.warn("实例修改失败 {}", e);
            return ResultObjectEnums.FAIL.getResultObject();
        }
        return ResultObjectEnums.SUCCESS.getResultObject();
    }

    /**
     * 模糊查询多个实例
     *
     * @param params
     * @return
     */
    @PostMapping("/queryRuntimesByKeyword")
    @ApiOperation(value = "模糊查询多个实例")
    public List<RuntimeEntity> queryRuntimesByKeyword(@RequestBody HashMap<String, String> params) {
        try {
            return runtimeService.queryRuntimeEntitysByKeyword(params.get("keyword"));
        } catch (Exception e) {
            log.warn("实例查询失败 {}", e);
            return null;
        }
    }

    /**
     * 查询多个实例
     *
     * @param runtimeEntity
     * @return
     */
    @PostMapping("/queryRuntimes")
    @ApiOperation(value = "查询多个实例")
    public List<RuntimeEntity> queryRuntimes(@RequestBody(required = false) RuntimeEntity runtimeEntity) {
        try {
            return runtimeService.queryRuntimeEntitys(runtimeEntity);
        } catch (Exception e) {
            log.warn("实例查询失败 {}", e);
            return null;
        }
    }

    /**
     * 查询单个实例
     *
     * @param runtimeEntity
     * @return
     */
    @PostMapping("/queryRuntime")
    @ApiOperation(value = "查询单个实例")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", paramType = "body", dataType = "Long", dataTypeClass = java.lang.Long.class, defaultValue = "1")
    })
    public RuntimeEntity queryRuntime(@ApiIgnore @RequestBody RuntimeEntity runtimeEntity) {
        try {
            return runtimeService.queryRuntimeEntity(runtimeEntity);
        } catch (Exception e) {
            log.warn("实例查询失败 {}", e);
            return null;
        }
    }
}
