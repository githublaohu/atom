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
package com.lamp.atom.service.operator.consumers.controller;

import com.lamp.atom.service.operator.entity.TaskEntity;
import com.lamp.atom.service.operator.service.TaskService;
import com.lamp.atom.service.operator.consumers.utils.ResultObjectEnums;
import com.lamp.decoration.core.result.ResultObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@Slf4j
@RequestMapping("/task")
@RestController("taskController")
public class TaskController {

    @Reference
    private TaskService taskService;

    /**
     * 添加任务
     * @param taskEntity
     */
    @PostMapping("/insertTask")
    public ResultObject<String> insertTask(@RequestBody TaskEntity taskEntity){
        //字段判空
        if (Objects.isNull(taskEntity.getOperatorId())) {
            log.info("参数校验失败 {}", taskEntity);
            return ResultObjectEnums.CHECK_PARAMETERS_FAIL.getResultObject();
        }
        try {
            taskService.insertTaskEntity(taskEntity);
        } catch (Exception e) {
            log.warn("任务插入失败 {}", e);
            return ResultObjectEnums.FAIL.getResultObject();
        }
        return ResultObjectEnums.SUCCESS.getResultObject();
    }

    /**
     * 修改任务
     * @param taskEntity
     * @return
     */
    @PostMapping("/updateTask")
    public ResultObject<String> updateTask(@RequestBody TaskEntity taskEntity){
        try {
            taskService.updateTaskEntity(taskEntity);
        } catch (Exception e) {
            log.warn("任务修改失败 {}", e);
            return ResultObjectEnums.FAIL.getResultObject();
        }
        return ResultObjectEnums.SUCCESS.getResultObject();
    }

    /**
     * 模糊查询多个任务
     * @param keyword
     * @return
     */
    @PostMapping("/queryTasksByKeyword")
    public List<TaskEntity> queryTasksByKeyword(@RequestBody String keyword){
        try {
            return taskService.queryTaskEntitysByKeyword(keyword);
        } catch (Exception e) {
            log.warn("任务查询失败 {}", e);
            return null;
        }
    }

    /**
     * 查询多个任务
     * @param taskEntity
     * @return
     */
    @PostMapping("/queryTasks")
    public List<TaskEntity> queryTasks(@RequestBody TaskEntity taskEntity){
        try {
            return taskService.queryTaskEntitys(taskEntity);
        } catch (Exception e) {
            log.warn("任务查询失败 {}", e);
            return null;
        }
    }

    /**
     * 查询单个任务
     * @param taskEntity
     * @return
     */
    @PostMapping("/queryTask")
    public TaskEntity queryTask(@RequestBody TaskEntity taskEntity){
        try {
            return taskService.queryTaskEntity(taskEntity);
        } catch (Exception e) {
            log.warn("任务查询失败 {}", e);
            return null;
        }
    }
}
