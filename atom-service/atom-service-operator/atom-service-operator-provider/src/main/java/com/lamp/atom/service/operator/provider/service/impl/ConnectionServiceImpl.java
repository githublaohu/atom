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

import com.lamp.atom.service.operator.entity.ConnectionEntity;
import com.lamp.atom.service.operator.provider.mapper.ConnectionMapper;
import com.lamp.atom.service.operator.service.ConnectionService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
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
    public List<ConnectionEntity> queryConnectionEntitysByKeyword(String keyword) {
        return connectionMapper.queryConnectionEntitysByKeyword(keyword);
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
