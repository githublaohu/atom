package com.lamp.atom.service.task.mapper;

import com.lamp.atom.service.task.entity.TaskEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface TaskMapper {
    /**
     * 添加任务
     * @param taskEntity
     */
    @Insert("insert into task" +
            "(task_name,explanation,start_time,end_time,scene_id,operator_id,task_lifecycle,task_status" +
            "values(#{taskName},#{explanation},#{startTime},#{endTime},#{sceneId},#{operatorId},#{taskLifecycle},#{taskStatus})")
    Integer insertTaskEntity(TaskEntity taskEntity);

    /**
     * 修改任务
     * @param taskEntity
     * @return
     */
    @Update("update task set " +
            "delete_flag = ${deleteFlag} " +
            "where id = #{id}")
    Integer updateTaskEntity(TaskEntity taskEntity);

    /**
     * 查询多个任务
     * @param taskEntity
     * @return
     */
    @Select({"<script>" +
            "select * from task " +
            "<where>" +
            "<if test = 'sceneId != null'>scene_id = #{sceneId} </if>" +
            "<if test = 'operatorId != null'>and operator_id = #{operatorId} </if>" +
            "<if test = 'startTime != null'>and start_time &gt; #{startTime} </if>" +
            "<if test = 'endTime != null'>and end_time &lt; #{endTime} </if>" +
            "<if test = 'taskLifecycle != null'>and task_lifecycle = #{taskLifecycle} </if>" +
            "<if test = 'taskStatus != null'>and task_status = #{taskStatus} </if>" +
            "</where>" +
            "</script>"})
    List<TaskEntity> queryTaskEntitys(TaskEntity taskEntity);

    /**
     * 查询单个任务
     * @param taskEntity
     * @return
     */
    @Select("select * from task " +
            "where id = #{id} and delete_flag = 0")
    TaskEntity queryTaskEntity(TaskEntity taskEntity);
}
