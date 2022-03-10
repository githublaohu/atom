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

import com.lamp.atom.service.space.entity.ResourceAccountEntity;
import com.lamp.atom.service.space.provider.mapper.ResourceAccountMapper;
import com.lamp.atom.service.space.service.ResourceAccountService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ResourceAccountServiceImpl implements ResourceAccountService {

    @Autowired
    private ResourceAccountMapper resourceAccountMapper;

    @Override
    public Integer insertResourceAccountEntity(ResourceAccountEntity resourceAccountEntity) {
        return resourceAccountMapper.insertResourceAccountEntity(resourceAccountEntity);
    }

    @Override
    public List<ResourceAccountEntity> queryResourceAccountEntitysByKeyword(String keyword) {
        return resourceAccountMapper.queryResourceAccountEntitysByKeyword(keyword);
    }

    @Override
    public Integer updateResourceAccountEntity(ResourceAccountEntity resourceAccountEntity) {
        return resourceAccountMapper.updateResourceAccountEntity(resourceAccountEntity);
    }

    @Override
    public List<ResourceAccountEntity> queryResourceAccountEntitys(ResourceAccountEntity resourceAccountEntity) {
        return resourceAccountMapper.queryResourceAccountEntitys(resourceAccountEntity);
    }

    @Override
    public ResourceAccountEntity queryResourceAccountEntity(ResourceAccountEntity resourceAccountEntity) {
        return resourceAccountMapper.queryResourceAccountEntity(resourceAccountEntity);
    }
}
