package com.lamp.atom.service.operator.service;

import com.lamp.atom.service.operator.entity.TaskEntity;

import java.util.List;

public interface TaskService {
    /**
     * 添加任务
     * @param taskEntity
     */
    Integer insertTaskEntity(TaskEntity taskEntity);

    /**
     * 修改任务
     * @param taskEntity
     * @return
     */
    Integer updateTaskEntity(TaskEntity taskEntity);

    /**
     * 查询多个任务
     * @param taskEntity
     * @return
     */
    List<TaskEntity> queryTaskEntitys(TaskEntity taskEntity);

    /**
     * 查询单个任务
     * @param taskEntity
     * @return
     */
    TaskEntity queryTaskEntity(TaskEntity taskEntity);
}
