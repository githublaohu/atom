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


import com.lamp.atom.service.operator.entity.ConnectionEntity;
import com.lamp.atom.service.operator.service.ConnectionService;
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
import java.util.Objects;

@Slf4j
@RequestMapping("/connection")
@RestController("connectionController")
@Api(tags = {"连接操作接口"})
public class ConnectionController {

    @Reference
    private ConnectionService connectionService;

    /**
     * 添加连接
     *
     * @param connectionEntity
     */
    @PostMapping("/insertConnection")
    @ApiOperation(value = "添加连接")
    public ResultObject<String> insertConnection(@RequestBody ConnectionEntity connectionEntity) {
        //字段判空
        if (Objects.isNull(connectionEntity.getSpaceId())) {
            log.info("参数校验失败 {}", connectionEntity);
            return ResultObjectEnums.CHECK_PARAMETERS_FAIL.getResultObject();
        }
        try {
            connectionService.insertConnectionEntity(connectionEntity);
        } catch (Exception e) {
            log.warn("连接插入失败 {}", e);
            return ResultObjectEnums.FAIL.getResultObject();
        }
        return ResultObjectEnums.SUCCESS.getResultObject();
    }

    /**
     * 修改连接
     *
     * @param connectionEntity
     * @return
     */
    @PostMapping("/updateConnection")
    @ApiOperation(value = "修改连接")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataTypeClass = java.lang.Long.class, paramType = "body", dataType = "Long"),
            @ApiImplicitParam(name = "deleteFlag", dataTypeClass = java.lang.Long.class, paramType = "body", dataType = "Long")
    })
    public ResultObject<String> updateConnection(@ApiIgnore @RequestBody ConnectionEntity connectionEntity) {
        try {
            connectionService.updateConnectionEntity(connectionEntity);
        } catch (Exception e) {
            log.warn("连接修改失败 {}", e);
            return ResultObjectEnums.FAIL.getResultObject();
        }
        return ResultObjectEnums.SUCCESS.getResultObject();
    }

    /**
     * 模糊查询多个连接
     *
     * @param params
     * @return
     */
    @PostMapping("/queryConnectionsByKeyword")
    @ApiOperation(value = "模糊查询多个连接")
    public List<ConnectionEntity> queryConnectionsByKeyword(@RequestBody HashMap<String, String> params) {
        try {
            return connectionService.queryConnectionEntitysByKeyword(params.get("keyword"));
        } catch (Exception e) {
            log.warn("连接查询失败 {}", e);
            return null;
        }
    }

    /**
     * 查询多个连接
     *
     * @param connectionEntity
     * @return
     */
    @PostMapping("/queryConnections")
    @ApiOperation(value = "查询多个连接")
    public List<ConnectionEntity> queryConnections(@RequestBody(required = false) ConnectionEntity connectionEntity) {
        try {
            return connectionService.queryConnectionEntitys(connectionEntity);
        } catch (Exception e) {
            log.warn("连接查询失败 {}", e);
            return null;
        }
    }

    /**
     * 查询单个连接
     *
     * @param connectionEntity
     * @return
     */
    @PostMapping("/queryConnection")
    @ApiOperation(value = "查询单个连接")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", paramType = "body", dataType = "Long", dataTypeClass = java.lang.Long.class, defaultValue = "1")
    })
    public ConnectionEntity queryConnection(@ApiIgnore @RequestBody ConnectionEntity connectionEntity) {
        try {
            return connectionService.queryConnectionEntity(connectionEntity);
        } catch (Exception e) {
            log.warn("连接查询失败 {}", e);
            return null;
        }
    }
}
