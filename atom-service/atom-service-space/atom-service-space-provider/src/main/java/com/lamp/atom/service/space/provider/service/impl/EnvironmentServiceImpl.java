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
package com.lamp.atom.service.space.provider.service.impl;

import com.lamp.atom.service.space.entity.EnvironmentEntity;
import com.lamp.atom.service.space.provider.mapper.EnvironmentMapper;
import com.lamp.atom.service.space.service.EnvironmentService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EnvironmentServiceImpl implements EnvironmentService {

    @Autowired
    private EnvironmentMapper environmentMapper;

    @Override
    public Integer insertEnvironmentEntity(EnvironmentEntity environmentEntity) {
        return environmentMapper.insertEnvironmentEntity(environmentEntity);
    }

    @Override
    public List<EnvironmentEntity> queryEnvironmentEntitysByKeyword(String keyword) {
        return environmentMapper.queryEnvironmentEntitysByKeyword(keyword);
    }

    @Override
    public Integer updateEnvironmentEntity(EnvironmentEntity environmentEntity) {
        return environmentMapper.updateEnvironmentEntity(environmentEntity);
    }

    @Override
    public List<EnvironmentEntity> queryEnvironmentEntitys(EnvironmentEntity environmentEntity) {
        return environmentMapper.queryEnvironmentEntitys(environmentEntity);
    }

    @Override
    public EnvironmentEntity queryEnvironmentEntity(EnvironmentEntity environmentEntity) {
        return environmentMapper.queryEnvironmentEntity(environmentEntity);
    }
}
