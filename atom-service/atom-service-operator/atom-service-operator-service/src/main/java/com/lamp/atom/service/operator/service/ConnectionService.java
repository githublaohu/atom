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

import com.lamp.atom.service.operator.entity.ConnectionEntity;

import java.util.List;

public interface ConnectionService {
    /**
     * 添加连接
     *
     * @param connectionEntity
     */
    Integer insertConnectionEntity(ConnectionEntity connectionEntity);

    /**
     * 修改连接
     *
     * @param connectionEntity
     * @return
     */
    Integer updateConnectionEntity(ConnectionEntity connectionEntity);

    /**
     * 模糊查询多个连接
     *
     * @param keyword
     * @return
     */
    List<ConnectionEntity> queryConnectionEntitysByKeyword(String keyword);

    /**
     * 查询多个连接
     *
     * @param connectionEntity
     * @return
     */
    List<ConnectionEntity> queryConnectionEntitys(ConnectionEntity connectionEntity);

    /**
     * 查询单个连接
     *
     * @param connectionEntity
     * @return
     */
    ConnectionEntity queryConnectionEntity(ConnectionEntity connectionEntity);
}
