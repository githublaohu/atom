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

import com.lamp.atom.service.operator.entity.ModelEntity;

import java.util.List;

public interface ModelService {

    /**
     * 创建模型
     * @param modelEntity
     */
    Integer createModelEntity(ModelEntity modelEntity);

    /**
     * 添加模型
     * @param modelEntity
     */
    Integer insertModelEntity(ModelEntity modelEntity);

    /**
     * 模糊查询多个模型
     * @param keyword
     * @return
     */
    List<ModelEntity> queryModelEntitysByKeyword(String keyword);

    /**
     * 修改模型
     * @param modelEntity
     * @return
     */
    Integer updateModelEntity(ModelEntity modelEntity);

    /**
     * 查询多个模型
     * @param modelEntity
     * @return
     */
    List<ModelEntity> queryModelEntitys(ModelEntity modelEntity);

    /**
     * 查询单个模型
     * @param modelEntity
     * @return
     */
    ModelEntity queryModelEntity(ModelEntity modelEntity);
}
