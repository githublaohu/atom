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
package com.lamp.atom.service.operator.entity;

import com.lamp.atom.service.domain.ServiceRuntimePattern;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="ServiceInfoEntity",description="服务配置类")
public class ServiceInfoEntity extends BaseEntity {

    private static final long serialVersionUID = -2082481705511854778L;

    /**
     * 空间ID
     */
    private Long spaceId;

    /**
     * 服务名
     */
    private String siName;

    /**
     * 服务类型
     */
    private String siType;

    /**
     * 服务运行模式
     */
    private ServiceRuntimePattern siRuntimePattern;

    /**
     * 节点数量
     */
    private Integer siNodeNum;

    /**
     * 镜像名
     */
    private String siImageName;

    /**
     * 服务cpu配置量
     */
    private Integer siCpu;

    /**
     * 服务gpu配置量
     */
    private Integer siGpu;

    /**
     * 服务内存配置量，单位是M
     */
    private Integer siMemory;

    /**
     * 服务现存配置量
     */
    private Integer siDisplayMemory;

    /**
     * 服务标签
     */
    private String siLabel;

}