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

import com.lamp.atom.service.operator.entity.ResourceRelationEntity;

import java.util.List;

public interface ResourceRelationService {

    /**
     * 创建资源关系
     *
     * @param resourceRelationEntity
     */
    Integer createResourceRelationEntity(ResourceRelationEntity resourceRelationEntity);

    /**
     * 添加资源关系
     *
     * @param resourceRelationEntity
     */
    Integer insertResourceRelationEntity(ResourceRelationEntity resourceRelationEntity);

    /**
     * 模糊查询多个资源关系
     *
     * @param keyword
     * @return
     */
    List<ResourceRelationEntity> queryResourceRelationEntitysByKeyword(String keyword);

    /**
     * 修改资源关系
     *
     * @param resourceRelationEntity
     * @return
     */
    Integer updateResourceRelationEntity(ResourceRelationEntity resourceRelationEntity);

    /**
     * 查询多个资源关系
     *
     * @param resourceRelationEntity
     * @return
     */
    List<ResourceRelationEntity> queryResourceRelationEntitys(ResourceRelationEntity resourceRelationEntity);

    /**
     * 查询单个资源关系
     *
     * @param resourceRelationEntity
     * @return
     */
    ResourceRelationEntity queryResourceRelationEntity(ResourceRelationEntity resourceRelationEntity);
}
