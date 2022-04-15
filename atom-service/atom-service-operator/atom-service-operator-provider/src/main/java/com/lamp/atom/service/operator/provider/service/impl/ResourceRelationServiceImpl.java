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

import com.lamp.atom.service.operator.entity.ResourceRelationEntity;
import com.lamp.atom.service.operator.provider.mapper.ResourceRelationMapper;
import com.lamp.atom.service.operator.service.ResourceRelationService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ResourceRelationServiceImpl implements ResourceRelationService {

    @Autowired
    private ResourceRelationMapper resourceRelationMapper;

    @Override
    public Integer insertResourceRelationEntity(ResourceRelationEntity resourceRelationEntity) {
        return resourceRelationMapper.insertResourceRelationEntity(resourceRelationEntity);
    }

    @Override
    public Integer batchInsertResourceRelationEntity(List<ResourceRelationEntity> resourceRelationEntityList) {
        return resourceRelationMapper.batchInsertResourceRelationEntity(resourceRelationEntityList);
    }

    @Override
    public List<ResourceRelationEntity> queryResourceRelationEntitysByKeyword(String keyword) {
        return resourceRelationMapper.queryResourceRelationEntitysByKeyword(keyword);
    }

    @Override
    public Integer updateResourceRelationEntity(ResourceRelationEntity resourceRelationEntity) {
        return resourceRelationMapper.updateResourceRelationEntity(resourceRelationEntity);
    }

    @Override
    public List<ResourceRelationEntity> queryResourceRelationEntitys(ResourceRelationEntity resourceRelationEntity) {
        return resourceRelationMapper.queryResourceRelationEntitys(resourceRelationEntity);
    }

    @Override
    public ResourceRelationEntity queryResourceRelationEntity(ResourceRelationEntity resourceRelationEntity) {
        return resourceRelationMapper.queryResourceRelationEntity(resourceRelationEntity);
    }
}
