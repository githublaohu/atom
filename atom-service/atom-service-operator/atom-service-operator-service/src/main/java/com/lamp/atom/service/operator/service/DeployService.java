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

import com.lamp.atom.service.operator.entity.DeployEntity;

import java.util.List;

public interface DeployService {

    /**
     * 创建部署
     *
     * @param deployEntity
     */
    Integer createDeployEntity(DeployEntity deployEntity);

    /**
     * 添加部署
     *
     * @param deployEntity
     */
    Integer insertDeployEntity(DeployEntity deployEntity);

    /**
     * 模糊查询多个部署
     *
     * @param keyword
     * @return
     */
    List<DeployEntity> queryDeployEntitysByKeyword(String keyword);

    /**
     * 修改部署
     *
     * @param deployEntity
     * @return
     */
    Integer updateDeployEntity(DeployEntity deployEntity);

    /**
     * 查询多个部署
     *
     * @param deployEntity
     * @return
     */
    List<DeployEntity> queryDeployEntitys(DeployEntity deployEntity);

    /**
     * 查询单个部署
     *
     * @param id
     * @return
     */
    DeployEntity queryDeployEntity(Long id);
}
