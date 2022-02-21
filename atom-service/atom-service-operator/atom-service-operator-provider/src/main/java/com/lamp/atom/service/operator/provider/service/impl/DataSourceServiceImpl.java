package com.lamp.atom.service.operator.provider.service.impl;

import com.lamp.atom.service.operator.entity.DataSourceEntity;
import com.lamp.atom.service.operator.provider.mapper.DataSourceMapper;
import com.lamp.atom.service.operator.service.DataSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service("dataSourceService")
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
    public List<DataSourceEntity> queryDataSourceEntitys(DataSourceEntity dataSourceEntity) {
        return dataSourceMapper.queryDataSourceEntitys(dataSourceEntity);
    }

    @Override
    public DataSourceEntity queryDataSourceEntity(DataSourceEntity dataSourceEntity) {
        return dataSourceMapper.queryDataSourceEntity(dataSourceEntity);
    }
}
