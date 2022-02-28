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

import com.lamp.atom.service.operator.entity.ModelEntity;
import com.lamp.atom.service.operator.provider.mapper.ModelMapper;
import com.lamp.atom.service.operator.service.ModelService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ModelServiceImpl implements ModelService {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Integer createModelEntity(ModelEntity modelEntity) {
        return null;
    }

    @Override
    public Integer insertModelEntity(ModelEntity modelEntity) {
        return modelMapper.insertModelEntity(modelEntity);
    }

    @Override
    public List<ModelEntity> queryModelEntitysByKeyword(String keyword) {
        return modelMapper.queryModelEntitysByKeyword(keyword);
    }

    @Override
    public Integer updateModelEntity(ModelEntity modelEntity) {
        return modelMapper.updateModelEntity(modelEntity);
    }

    @Override
    public List<ModelEntity> queryModelEntitys(ModelEntity modelEntity) {
        return modelMapper.queryModelEntitys(modelEntity);
    }

    @Override
    public ModelEntity queryModelEntity(ModelEntity modelEntity) {
        return modelMapper.queryModelEntity(modelEntity);
    }
}
