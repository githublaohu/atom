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

import com.lamp.atom.service.domain.OrganizationStatus;
import com.lamp.atom.service.domain.OrganizationType;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="OrganizationEntity",description="组织类")
public class OrganizationEntity extends BaseEntity {

    private static final long serialVersionUID = 8141439293056173411L;

    /**
     * 父ID
     */
    private Long parentId;

    /**
     * 组织名
     */
    private String organizationName;

    /**
     * 组织别名
     */
    private String organizationAlias;

    /**
     * 组织类型
     */
    private OrganizationType organizationType;

    /**
     * 说明
     */
    private String explaination;

    /**
     * 创建人ID
     */
    private Long createId;

    /**
     * 创建名
     */
    private String createName;

    /**
     * 拥有人ID
     */
    private Long ownerId;

    /**
     * 拥有人名
     */
    private String ownerName;

    /**
     * 组织状态
     */
    private OrganizationStatus organizationStatus;

    public OrganizationEntity(Long id) {
        super(id);
    }
}
