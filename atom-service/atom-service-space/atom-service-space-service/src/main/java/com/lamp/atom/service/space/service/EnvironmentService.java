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

import com.lamp.atom.service.space.entity.EnvironmentEntity;

import java.util.List;

public interface EnvironmentService {

    /**
     * 添加环境
     * @param environmentEntity
     */
    Integer insertEnvironmentEntity(EnvironmentEntity environmentEntity);

    /**
     * 模糊查询多个环境
     * @param keyword
     * @return
     */
    List<EnvironmentEntity> queryEnvironmentEntitysByKeyword(String keyword);

    /**
     * 修改环境
     * @param environmentEntity
     * @return
     */
    Integer updateEnvironmentEntity(EnvironmentEntity environmentEntity);

    /**
     * 查询多个环境
     * @param environmentEntity
     * @return
     */
    List<EnvironmentEntity> queryEnvironmentEntitys(EnvironmentEntity environmentEntity);

    /**
     * 查询单个环境
     * @param environmentEntity
     * @return
     */
    EnvironmentEntity queryEnvironmentEntity(EnvironmentEntity environmentEntity);
}
