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
package com.lamp.atom.service.space.service;

import com.lamp.atom.service.space.entity.ResourceAccountEntity;

import java.util.List;

public interface ResourceAccountService {

    /**
     * 添加资源账户
     * @param resourceAccountEntity
     */
    Integer insertResourceAccountEntity(ResourceAccountEntity resourceAccountEntity);

    /**
     * 模糊查询多个资源账户
     * @param keyword
     * @return
     */
    List<ResourceAccountEntity> queryResourceAccountEntitysByKeyword(String keyword);

    /**
     * 修改资源账户
     * @param resourceAccountEntity
     * @return
     */
    Integer updateResourceAccountEntity(ResourceAccountEntity resourceAccountEntity);

    /**
     * 查询多个资源账户
     * @param resourceAccountEntity
     * @return
     */
    List<ResourceAccountEntity> queryResourceAccountEntitys(ResourceAccountEntity resourceAccountEntity);

    /**
     * 查询单个资源账户
     * @param resourceAccountEntity
     * @return
     */
    ResourceAccountEntity queryResourceAccountEntity(ResourceAccountEntity resourceAccountEntity);
}
