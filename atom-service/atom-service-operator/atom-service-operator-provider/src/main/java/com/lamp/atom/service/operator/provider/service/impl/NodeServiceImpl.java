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

import com.lamp.atom.service.operator.entity.NodeEntity;
import com.lamp.atom.service.operator.provider.mapper.NodeMapper;
import com.lamp.atom.service.operator.service.NodeService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class NodeServiceImpl implements NodeService {

    @Autowired
    private NodeMapper nodeMapper;

    @Override
    public Integer createNodeEntity(NodeEntity nodeEntity) {
        return null;
    }

    @Override
    public Integer insertNodeEntity(NodeEntity nodeEntity) {
        return nodeMapper.insertNodeEntity(nodeEntity);
    }

    @Override
    public List<NodeEntity> queryNodeEntitysByKeyword(String keyword) {
        return nodeMapper.queryNodeEntitysByKeyword(keyword);
    }

    @Override
    public Integer updateNodeEntity(NodeEntity nodeEntity) {
        return nodeMapper.updateNodeEntity(nodeEntity);
    }

    @Override
    public List<NodeEntity> queryNodeEntitys(NodeEntity nodeEntity) {
        return nodeMapper.queryNodeEntitys(nodeEntity);
    }

    @Override
    public NodeEntity queryNodeEntity(NodeEntity nodeEntity) {
        return nodeMapper.queryNodeEntity(nodeEntity);
    }
}
