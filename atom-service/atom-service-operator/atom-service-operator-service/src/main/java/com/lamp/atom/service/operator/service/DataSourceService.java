package com.lamp.atom.service.operator.service;

import com.lamp.atom.service.operator.entity.DataSourceEntity;

import java.util.List;

public interface DataSourceService {
    /**
     * 添加数据源
     * @param dataSourceEntity
     */
    Integer insertDataSourceEntity(DataSourceEntity dataSourceEntity);

    /**
     * 修改数据源
     * @param dataSourceEntity
     * @return
     */
    Integer updateDataSourceEntity(DataSourceEntity dataSourceEntity);

    /**
     * 查询多个数据源
     * @param dataSourceEntity
     * @return
     */
    List<DataSourceEntity> queryDataSourceEntitys(DataSourceEntity dataSourceEntity);

    /**
     * 查询单个数据源
     * @param dataSourceEntity
     * @return
     */
    DataSourceEntity queryDataSourceEntity(DataSourceEntity dataSourceEntity);
}
