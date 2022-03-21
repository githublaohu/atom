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

import com.lamp.atom.service.operator.entity.DeployEntity;
import com.lamp.atom.service.operator.provider.mapper.DeployMapper;
import com.lamp.atom.service.operator.service.DeployService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DeployServiceImpl implements DeployService {

    @Autowired
    private DeployMapper deployMapper;

    @Override
    public Integer createDeployEntity(DeployEntity deployEntity) {
        return null;
    }

    @Override
    public Integer insertDeployEntity(DeployEntity deployEntity) {
        return deployMapper.insertDeployEntity(deployEntity);
    }

    @Override
    public List<DeployEntity> queryDeployEntitysByKeyword(String keyword) {
        return deployMapper.queryDeployEntitysByKeyword(keyword);
    }

    @Override
    public Integer updateDeployEntity(DeployEntity deployEntity) {
        return deployMapper.updateDeployEntity(deployEntity);
    }

    @Override
    public List<DeployEntity> queryDeployEntitys(DeployEntity deployEntity) {
        return deployMapper.queryDeployEntitys(deployEntity);
    }

    @Override
    public DeployEntity queryDeployEntity(Long id) {
        return deployMapper.queryDeployEntity(id);
    }
}
