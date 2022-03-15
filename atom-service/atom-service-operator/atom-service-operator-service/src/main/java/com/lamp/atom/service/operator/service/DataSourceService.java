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
package com.lamp.atom.service.operator.service;

import com.lamp.atom.service.operator.entity.DataSourceEntity;

import java.util.List;

public interface DataSourceService {
    /**
     * 添加数据源
     *
     * @param dataSourceEntity
     */
    Integer insertDataSourceEntity(DataSourceEntity dataSourceEntity);

    /**
     * 修改数据源
     *
     * @param dataSourceEntity
     * @return
     */
    Integer updateDataSourceEntity(DataSourceEntity dataSourceEntity);

    /**
     * 模糊查询多个数据源
     *
     * @param keyword
     * @return
     */
    List<DataSourceEntity> queryDataSourceEntitysByKeyword(String keyword);

    /**
     * 查询多个数据源
     *
     * @param dataSourceEntity
     * @return
     */
    List<DataSourceEntity> queryDataSourceEntitys(DataSourceEntity dataSourceEntity);

    /**
     * 查询单个数据源
     *
     * @param dataSourceEntity
     * @return
     */
    DataSourceEntity queryDataSourceEntity(DataSourceEntity dataSourceEntity);
}
