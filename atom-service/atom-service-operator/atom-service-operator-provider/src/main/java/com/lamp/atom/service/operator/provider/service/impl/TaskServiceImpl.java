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
package com.lamp.atom.service.operator.provider.service.impl;

import com.lamp.atom.service.operator.entity.TaskEntity;
import com.lamp.atom.service.operator.provider.mapper.TaskMapper;
import com.lamp.atom.service.operator.service.TaskService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
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
    public List<TaskEntity> queryTaskEntitysByKeyword(String keyword) {
        return taskMapper.queryTaskEntitysByKeyword(keyword);
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
