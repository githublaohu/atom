package com.lamp.atom.service.operator.provider.service.impl;

import com.lamp.atom.service.operator.entity.ConnectionEntity;
import com.lamp.atom.service.operator.provider.mapper.ConnectionMapper;
import com.lamp.atom.service.operator.service.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service("connectionService")
public class ConnectionServiceImpl implements ConnectionService {

    @Autowired
    private ConnectionMapper connectionMapper;

    @Override
    public Integer insertConnectionEntity(ConnectionEntity connectionEntity) {
        return connectionMapper.insertConnectionEntity(connectionEntity);
    }

    @Override
    public Integer updateConnectionEntity(ConnectionEntity connectionEntity) {
        return connectionMapper.updateConnectionEntity(connectionEntity);
    }

    @Override
    public List<ConnectionEntity> queryConnectionEntitys(ConnectionEntity connectionEntity) {
        return connectionMapper.queryConnectionEntitys(connectionEntity);
    }

    @Override
    public ConnectionEntity queryConnectionEntity(ConnectionEntity connectionEntity) {
        return connectionMapper.queryConnectionEntity(connectionEntity);
    }
}
