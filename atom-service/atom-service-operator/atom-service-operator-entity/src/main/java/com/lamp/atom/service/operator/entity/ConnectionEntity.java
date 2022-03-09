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

import com.lamp.atom.service.domain.ColonyType;
import com.lamp.atom.service.domain.SourceType;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="ConnectionEntity",description="连接实体")
public class ConnectionEntity extends BaseEntity {

    private static final long serialVersionUID = 7945055154629636963L;

    /**
     * 空间id
     */
    private Long spaceId;

    /**
     * 操作类型
     */
    private String operationType;

    /**
     * 数据源类型
     */
    private SourceType sourceType;

    /**
     * 数据源名
     */
    private String sourceName;

    /**
     * 源数据地址
     */
    private String sourceAddr;

    /**
     * 源登录账户
     */
    private String sourceAccount;

    /**
     * 源登录密码
     */
    private String sourcePassword;

    /**
     * 源登录空间：关系型数据库的数据库，oss的bucket,redis的index,es的index
     */
    private String sourceSpace;

    /**
     * 模式
     */
    private String mode;

    /**
     * 集群模式
     */
    private ColonyType colonyType;

    /**
     * 源数据配置
     */
    private String sourceConf;

    /**
     * 源数据路径
     */
    private String sourceRoute;

    /**
     * 源数据大小
     */
    private String sourceSize;

    /**
     * 源数据条数
     */
    private Long sourceCount;

    public ConnectionEntity(Long id) {
        super(id);
    }
}
