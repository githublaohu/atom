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

import com.lamp.atom.service.domain.RelationType;
import com.lamp.atom.service.domain.ResourceType;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="ResourceRelationEntity",description="关联关系类")
public class ResourceRelationEntity extends BaseEntity {

    private static final long serialVersionUID = -3149595542813993064L;

    /**
     * 关联ID
     */
    private Long relateId;

    /**
     * 关联类型
     */
    private ResourceType relateType;

    /**
     * 被关联ID
     */
    private Long beRelatedId;

    /**
     * 被关联类型
     */
    private ResourceType beRelatedType;

    /**
     * 关系类型
     */
    private RelationType relationType;

    /**
     * 关联状态
     */
    private String relationStatus;

    /**
     * 顺序
     */
    private Integer order;

}
