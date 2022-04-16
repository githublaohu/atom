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
package com.lamp.atom.service.operator.provider.service.impl;

import com.lamp.atom.service.operator.entity.DataSourceEntity;
import com.lamp.atom.service.operator.provider.mapper.DataSourceMapper;
import com.lamp.atom.service.operator.service.DataSourceService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DataSourceServiceImpl implements DataSourceService {

    @Autowired
    private DataSourceMapper dataSourceMapper;

    @Override
    public Integer insertDataSourceEntity(DataSourceEntity dataSourceEntity) {
        return dataSourceMapper.insertDataSourceEntity(dataSourceEntity);
    }

    @Override
    public Integer updateDataSourceEntity(DataSourceEntity dataSourceEntity) {
        return dataSourceMapper.updateDataSourceEntity(dataSourceEntity);
    }

    @Override
    public List<DataSourceEntity> queryDataSourceEntitysByKeyword(String keyword) {
        return dataSourceMapper.queryDataSourceEntitysByKeyword(keyword);
    }

    @Override
    public List<DataSourceEntity> queryDataSourceEntitys(DataSourceEntity dataSourceEntity) {
        return dataSourceMapper.queryDataSourceEntitys(dataSourceEntity);
    }

    @Override
    public DataSourceEntity queryDataSourceEntity(DataSourceEntity dataSourceEntity) {
        return dataSourceMapper.queryDataSourceEntity(dataSourceEntity);
    }
}
