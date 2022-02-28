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
package com.lamp.atom.service.operator.provider.mapper;

import com.lamp.atom.service.operator.entity.TaskEntity;
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
     * 模糊查询多个任务
     * @param keyword
     * @return
     */
    @Select({"select * from model " +
            "where" +
            "id like #{keyword} or task_name like #{keyword} or explanation like #{keyword} " +
            "or start_time like #{keyword} or end_time like #{keyword} or scene_id like #{keyword} " +
            "or operator_id like #{keyword} or task_lifecycle like #{keyword} or task_status like #{keyword}"})
    List<TaskEntity> queryTaskEntitysByKeyword(String keyword);

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
