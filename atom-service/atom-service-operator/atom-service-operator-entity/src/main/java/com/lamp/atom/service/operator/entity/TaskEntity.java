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

import com.lamp.atom.service.domain.OperatorRuntimeType;
import com.lamp.atom.service.domain.TaskLifecycle;
import com.lamp.atom.service.domain.TaskStatus;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;

@Data
public class TaskEntity extends BaseEntity {

    private static final long serialVersionUID = 6643322885682857016L;

    /**
     * 节点名
     */
    private String taskName;

    /**
     * 说明
     */
    private String explanation;

    /**
     * 节点开始时间
     */
    private Date startTime;

    /**
     * 节点结束时间
     */
    private Date endTime;

    /**
     * 空间id
     */
    @NonNull
    private Long spaceId;

    /**
     * 场景id
     */
    @NonNull
    private Long sceneId;

    /**
     * 算子id
     */
    @NonNull
    private Long operatorId;

    /**
     * 算子类型
     */
    @NonNull
    private OperatorRuntimeType operatorRuntimeType;

    /**
     * 任务生命周期
     */
    @NonNull
    private TaskLifecycle taskLifecycle;

    /**
     * 任务运行状态
     */
    @NonNull
    private TaskStatus taskStatus;
}
