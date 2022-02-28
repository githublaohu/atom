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
import com.lamp.atom.service.operator.entity.ModelEntity;
import com.lamp.atom.service.operator.service.DataSourceService;
import com.lamp.atom.service.operator.consumers.utils.ResultObjectEnums;
import com.lamp.decoration.core.result.ResultObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@Slf4j
@RequestMapping("/dataSource")
@RestController("dataSourceController")
public class DataSourceController {

    @Reference
    private DataSourceService dataSourceService;

    /**
     * 添加数据源
     * @param dataSourceEntity
     */
    @PostMapping("/insertDataSource")
    public ResultObject<String> insertDataSource(@RequestBody DataSourceEntity dataSourceEntity){
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
     * @param dataSourceEntity
     * @return
     */
    @PostMapping("/updateDataSource")
    public ResultObject<String> updateDataSource(@RequestBody DataSourceEntity dataSourceEntity){
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
     * @param keyword
     * @return
     */
    @PostMapping("/queryDataSourcesByKeyword")
    public List<DataSourceEntity> queryDataSourcesByKeyword(@RequestBody String keyword){
        try {
            return dataSourceService.queryDataSourceEntitysByKeyword(keyword);
        } catch (Exception e) {
            log.warn("数据源查询失败 {}", e);
            return null;
        }
    }

    /**
     * 查询多个数据源
     * @param dataSourceEntity
     * @return
     */
    @PostMapping("/queryDataSources")
    public List<DataSourceEntity> queryDataSources(@RequestBody DataSourceEntity dataSourceEntity){
        try {
            return dataSourceService.queryDataSourceEntitys(dataSourceEntity);
        } catch (Exception e) {
            log.warn("数据源查询失败 {}", e);
            return null;
        }
    }

    /**
     * 查询单个数据源
     * @param dataSourceEntity
     * @return
     */
    @PostMapping("/queryDataSource")
    public DataSourceEntity queryDataSource(@RequestBody DataSourceEntity dataSourceEntity){
        try {
            DataSourceEntity dataSourceEntity1 = dataSourceService.queryDataSourceEntity(dataSourceEntity);
            return dataSourceEntity1;
        } catch (Exception e) {
            log.warn("数据源查询失败 {}", e);
            return null;
        }
    }
}
