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

import com.lamp.atom.service.operator.entity.NodeEntity;

import java.util.List;

public interface NodeService {

    /**
     * 创建节点
     *
     * @param nodeEntity
     */
    Integer createNodeEntity(NodeEntity nodeEntity);

    /**
     * 添加节点
     *
     * @param nodeEntity
     */
    Integer insertNodeEntity(NodeEntity nodeEntity);

    /**
     * 模糊查询多个节点
     *
     * @param keyword
     * @return
     */
    List<NodeEntity> queryNodeEntitysByKeyword(String keyword);

    /**
     * 修改节点
     *
     * @param nodeEntity
     * @return
     */
    Integer updateNodeEntity(NodeEntity nodeEntity);

    /**
     * 查询多个节点
     *
     * @param nodeEntity
     * @return
     */
    List<NodeEntity> queryNodeEntitys(NodeEntity nodeEntity);

    /**
     * 查询单个节点
     *
     * @param nodeEntity
     * @return
     */
    NodeEntity queryNodeEntity(NodeEntity nodeEntity);
}
