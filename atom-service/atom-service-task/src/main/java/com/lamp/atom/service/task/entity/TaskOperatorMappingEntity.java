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
