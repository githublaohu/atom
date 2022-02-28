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
package com.lamp.atom.service.task.entity;

import com.lamp.atom.service.domain.TaskStatus;
import lombok.Data;

import java.util.Map;

@Data
public class TaskOperatorMappingEntity extends BaseEntity {
    /**
     * 任务id
     */
    private Long taskId;

    /**
     * 算子id
     */
    private Long operatorId;

    /**
     * 前置任务与任务运行状态
     */
    private Map<Long, TaskStatus> preTaskAndTaskStatusMapping;

    /**
     * 后置任务与任务运行状态
     */
    private Map<Long, TaskStatus> nextTaskAndTaskStatusMapping;

}
