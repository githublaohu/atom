package com.lamp.atom.service.operator.consumers.controller;


import com.lamp.atom.service.operator.entity.DataSourceEntity;
import com.lamp.atom.service.operator.service.DataSourceService;
import com.lamp.atom.service.operator.consumers.utils.ResultObjectEnums;
import com.lamp.decoration.core.result.ResultObject;
import lombok.extern.slf4j.Slf4j;
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

    @Autowired
    @Qualifier("dataSourceService")
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
