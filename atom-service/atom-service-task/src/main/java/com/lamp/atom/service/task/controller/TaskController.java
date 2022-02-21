package com.lamp.atom.service.task.controller;

import com.lamp.atom.service.task.utils.ResultObjectEnums;
import com.lamp.atom.service.task.entity.TaskEntity;
import com.lamp.atom.service.task.service.TaskService;
import com.lamp.decoration.core.result.ResultObject;
import lombok.extern.slf4j.Slf4j;
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

    @Autowired
    @Qualifier("taskService")
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
