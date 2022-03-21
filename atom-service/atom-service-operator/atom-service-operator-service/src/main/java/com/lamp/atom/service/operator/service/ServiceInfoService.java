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

import com.lamp.atom.service.operator.entity.ServiceInfoEntity;

import java.util.List;

public interface ServiceInfoService {

	/**
	 * 创建服务配置
	 *
	 * @param serviceInfoEntity
	 */
	Integer createServiceInfoEntity(ServiceInfoEntity serviceInfoEntity);

	/**
	 * 添加服务配置
	 *
	 * @param serviceInfoEntity
	 */
	Integer insertServiceInfoEntity(ServiceInfoEntity serviceInfoEntity);

	/**
	 * 模糊查询多个服务配置
	 *
	 * @param keyword
	 * @return
	 */
	List<ServiceInfoEntity> queryServiceInfoEntitysByKeyword(String keyword);

	/**
	 * 修改服务配置
	 *
	 * @param serviceInfoEntity
	 * @return
	 */
	Integer updateServiceInfoEntity(ServiceInfoEntity serviceInfoEntity);

    /**
     * 查询多个服务配置
     *
     * @param serviceInfoEntity
     * @return
     */
    List<ServiceInfoEntity> queryServiceInfoEntitys(ServiceInfoEntity serviceInfoEntity);

	/**
	 * 查询单个服务配置
	 *
	 * @param serviceInfoEntity
	 * @return
	 */
	ServiceInfoEntity queryServiceInfoEntity(ServiceInfoEntity serviceInfoEntity);

	/**
	 * 查询单个服务配置
	 *
	 * @param id
	 * @return
	 */
	ServiceInfoEntity queryServiceInfoEntityById(Long id);
}
