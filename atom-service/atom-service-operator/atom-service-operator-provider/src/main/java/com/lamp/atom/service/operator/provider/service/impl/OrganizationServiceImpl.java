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

import com.lamp.atom.service.operator.entity.OrganizationEntity;
import com.lamp.atom.service.operator.provider.mapper.OrganizationMapper;
import com.lamp.atom.service.operator.service.OrganizationService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationMapper organizationMapper;

    @Override
    public Integer createOrganizationEntity(OrganizationEntity organizationEntity) {
        return null;
    }

    @Override
    public Integer insertOrganizationEntity(OrganizationEntity organizationEntity) {
        return organizationMapper.insertOrganizationEntity(organizationEntity);
    }

    @Override
    public List<OrganizationEntity> queryOrganizationEntitysByKeyword(String keyword) {
        return organizationMapper.queryOrganizationEntitysByKeyword(keyword);
    }

    @Override
    public Integer updateOrganizationEntity(OrganizationEntity organizationEntity) {
        return organizationMapper.updateOrganizationEntity(organizationEntity);
    }

    @Override
    public List<OrganizationEntity> queryOrganizationEntitys(OrganizationEntity organizationEntity) {
        return organizationMapper.queryOrganizationEntitys(organizationEntity);
    }

    @Override
    public OrganizationEntity queryOrganizationEntity(OrganizationEntity organizationEntity) {
        return organizationMapper.queryOrganizationEntity(organizationEntity);
    }
}
