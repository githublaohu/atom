package com.lamp.atom.service.operator.service;

import com.lamp.atom.service.operator.entity.ConnectionEntity;

import java.util.List;

public interface ConnectionService {
    /**
     * 添加连接
     * @param connectionEntity
     */
    Integer insertConnectionEntity(ConnectionEntity connectionEntity);

    /**
     * 修改连接
     * @param connectionEntity
     * @return
     */
    Integer updateConnectionEntity(ConnectionEntity connectionEntity);

    /**
     * 查询多个连接
     * @param connectionEntity
     * @return
     */
    List<ConnectionEntity> queryConnectionEntitys(ConnectionEntity connectionEntity);

    /**
     * 查询单个连接
     * @param connectionEntity
     * @return
     */
    ConnectionEntity queryConnectionEntity(ConnectionEntity connectionEntity);
}
