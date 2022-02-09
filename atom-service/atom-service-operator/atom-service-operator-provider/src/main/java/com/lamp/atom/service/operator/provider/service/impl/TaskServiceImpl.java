package com.lamp.atom.service.operator.provider.service.impl;

import com.lamp.atom.service.operator.entity.TaskEntity;
import com.lamp.atom.service.operator.provider.mapper.TaskMapper;
import com.lamp.atom.service.operator.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service("taskService")
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskMapper taskMapper;

    @Override
    public Integer insertTaskEntity(TaskEntity taskEntity) {
        return taskMapper.insertTaskEntity(taskEntity);
    }

    @Override
    public Integer updateTaskEntity(TaskEntity taskEntity) {
        return taskMapper.updateTaskEntity(taskEntity);
    }

    @Override
    public List<TaskEntity> queryTaskEntitys(TaskEntity taskEntity) {
        return taskMapper.queryTaskEntitys(taskEntity);
    }

    @Override
    public TaskEntity queryTaskEntity(TaskEntity taskEntity) {
        return taskMapper.queryTaskEntity(taskEntity);
    }
}
