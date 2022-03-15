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
package com.lamp.atom.service.operator.service;

import com.lamp.atom.service.operator.entity.OrganizationEntity;

import java.util.List;

public interface OrganizationService {

    /**
     * 创建组织
     *
     * @param organizationEntity
     */
    Integer createOrganizationEntity(OrganizationEntity organizationEntity);

    /**
     * 添加组织
     *
     * @param organizationEntity
     */
    Integer insertOrganizationEntity(OrganizationEntity organizationEntity);

    /**
     * 模糊查询多个组织
     *
     * @param keyword
     * @return
     */
    List<OrganizationEntity> queryOrganizationEntitysByKeyword(String keyword);

    /**
     * 修改组织
     *
     * @param organizationEntity
     * @return
     */
    Integer updateOrganizationEntity(OrganizationEntity organizationEntity);

    /**
     * 查询多个组织
     *
     * @param organizationEntity
     * @return
     */
    List<OrganizationEntity> queryOrganizationEntitys(OrganizationEntity organizationEntity);

    /**
     * 查询单个组织
     *
     * @param organizationEntity
     * @return
     */
    OrganizationEntity queryOrganizationEntity(OrganizationEntity organizationEntity);
}
