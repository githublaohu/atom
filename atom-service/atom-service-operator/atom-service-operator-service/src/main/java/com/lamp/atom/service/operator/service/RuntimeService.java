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

import com.lamp.atom.service.operator.entity.RuntimeEntity;

import java.util.List;

public interface RuntimeService {
    /**
     * 添加运行实例
     *
     * @param runtimeEntity
     */
    Integer insertRuntimeEntity(RuntimeEntity runtimeEntity);

    /**
     * 批量添加运行实例
     *
     * @param runtimeEntityList
     */
    Integer batchInsertRuntimeEntity(List<RuntimeEntity> runtimeEntityList);

    /**
     * 修改运行实例
     *
     * @param runtimeEntity
     * @return
     */
    Integer updateRuntimeEntity(RuntimeEntity runtimeEntity);

    /**
     * 根据节点ID和模型创建类型修改状态
     */
    Integer updateRuntimeStatus(RuntimeEntity runtimeEntity);

    /**
     * 批量修改运行实例
     *
     * @param runtimeEntityList
     * @return
     */
    Integer batchUpdateRuntimeEntity(List<RuntimeEntity> runtimeEntityList);

    /**
     * 模糊查询多个运行实例
     *
     * @param keyword
     * @return
     */
    List<RuntimeEntity> queryRuntimeEntitysByKeyword(String keyword);

    /**
     * 查询多个运行实例
     *
     * @param runtimeEntity
     * @return
     */
    List<RuntimeEntity> queryRuntimeEntitys(RuntimeEntity runtimeEntity);

    /**
     * 查询单个运行实例
     *
     * @param runtimeEntity
     * @return
     */
    RuntimeEntity queryRuntimeEntity(RuntimeEntity runtimeEntity);
}
