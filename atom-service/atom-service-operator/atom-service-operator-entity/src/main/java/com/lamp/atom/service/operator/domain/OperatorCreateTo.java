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
package com.lamp.atom.service.operator.domain;

import com.lamp.atom.service.operator.entity.ConnectionEntity;
import com.lamp.atom.service.operator.entity.ModelEntity;
import com.lamp.atom.service.operator.entity.OperatorEntity;
//import com.lamp.atom.service.space.entity.ResourceAccountEntity;
import lombok.Data;

import java.util.List;

@Data
public class OperatorCreateTo {

    private List<SourceAndConnect> sourceAndConnects;

    private ModelEntity modelTo;

//    private ResourceAccountEntity resourceAcountTo;//todo 用户管理模块的字段

    private OperatorEntity operatorTo;

    private ConnectionEntity modelConnect;
}
