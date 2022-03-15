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


import com.lamp.atom.service.operator.entity.DataSourceEntity;
import com.lamp.atom.service.operator.service.DataSourceService;
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
@RequestMapping("/dataSource")
@RestController("dataSourceController")
@Api(tags = {"数据源操作接口"})
public class DataSourceController {

    @Reference
    private DataSourceService dataSourceService;

    /**
     * 添加数据源
     *
     * @param dataSourceEntity
     */
    @PostMapping("/insertDataSource")
    @ApiOperation(value = "添加数据源")
    public ResultObject<String> insertDataSource(@RequestBody DataSourceEntity dataSourceEntity) {
        //字段判空
        if (Objects.isNull(dataSourceEntity.getOperatorId()) || Objects.isNull(dataSourceEntity.getConnectionId())) {
            log.info("参数校验失败 {}", dataSourceEntity);
            return ResultObjectEnums.CHECK_PARAMETERS_FAIL.getResultObject();
        }
        try {
            dataSourceService.insertDataSourceEntity(dataSourceEntity);
        } catch (Exception e) {
            log.warn("数据源插入失败 {}", e);
            return ResultObjectEnums.FAIL.getResultObject();
        }
        return ResultObjectEnums.SUCCESS.getResultObject();
    }

    /**
     * 修改数据源
     *
     * @param dataSourceEntity
     * @return
     */
    @PostMapping("/updateDataSource")
    @ApiOperation(value = "修改数据源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataTypeClass = java.lang.Long.class, paramType = "body", dataType = "Long"),
            @ApiImplicitParam(name = "deleteFlag", dataTypeClass = java.lang.Long.class, paramType = "body", dataType = "Long")
    })
    public ResultObject<String> updateDataSource(@ApiIgnore @RequestBody DataSourceEntity dataSourceEntity) {
        try {
            dataSourceService.updateDataSourceEntity(dataSourceEntity);
        } catch (Exception e) {
            log.warn("数据源修改失败 {}", e);
            return ResultObjectEnums.FAIL.getResultObject();
        }
        return ResultObjectEnums.SUCCESS.getResultObject();
    }

    /**
     * 模糊查询多个数据源
     *
     * @param params
     * @return
     */
    @PostMapping("/queryDataSourcesByKeyword")
    @ApiOperation(value = "模糊查询多个数据源")
    public List<DataSourceEntity> queryDataSourcesByKeyword(@RequestBody HashMap<String, String> params) {
        try {
            return dataSourceService.queryDataSourceEntitysByKeyword(params.get("keyword"));
        } catch (Exception e) {
            log.warn("数据源查询失败 {}", e);
            return null;
        }
    }

    /**
     * 查询多个数据源
     *
     * @param dataSourceEntity
     * @return
     */
    @PostMapping("/queryDataSources")
    @ApiOperation(value = "查询多个数据源")
    public List<DataSourceEntity> queryDataSources(@RequestBody(required = false) DataSourceEntity dataSourceEntity) {
        try {
            return dataSourceService.queryDataSourceEntitys(dataSourceEntity);
        } catch (Exception e) {
            log.warn("数据源查询失败 {}", e);
            return null;
        }
    }

    /**
     * 查询单个数据源
     *
     * @param dataSourceEntity
     * @return
     */
    @PostMapping("/queryDataSource")
    @ApiOperation(value = "查询单个数据源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", paramType = "body", dataType = "Long", dataTypeClass = java.lang.Long.class, defaultValue = "1")
    })
    public DataSourceEntity queryDataSource(@ApiIgnore @RequestBody DataSourceEntity dataSourceEntity) {
        try {
            return dataSourceService.queryDataSourceEntity(dataSourceEntity);
        } catch (Exception e) {
            log.warn("数据源查询失败 {}", e);
            return null;
        }
    }
}
