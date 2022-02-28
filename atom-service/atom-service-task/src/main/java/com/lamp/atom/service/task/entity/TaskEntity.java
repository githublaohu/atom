package com.lamp.atom.service.task.entity;

import com.lamp.atom.service.domain.OperatorRuntimeType;
import com.lamp.atom.service.domain.TaskLifecycle;
import com.lamp.atom.service.domain.TaskStatus;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;

@Data
public class TaskEntity extends BaseEntity {
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
