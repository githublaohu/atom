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
            "(task_name,operator_id,operator_name,server_ip," +
            "start_time,end_time,estimate_start_time,estimate_end_time," +
            "status,sequence,reason_data_num,data_flow_num) " +
            "values(#{taskName},#{operatorId},#{operatorName},#{serverIp}," +
            "#{startTime},#{endTime},#{estimateStartTime},#{estimateEndTime}," +
            "#{status},#{sequence},#{reasonDataNum},#{dataFlowNum})")
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
            "<if test = 'operatorId != null'>operator_id = #{operatorId} </if>" +
            "<if test = 'startTime != null'>and start_time &gt; #{startTime} </if>" +
            "<if test = 'endTime != null'>and end_time &lt; #{endTime} </if>" +
            "<if test = 'estimateStartTime != null'>and estimate_start_time &gt; #{estimateStartTime} </if>" +
            "<if test = 'estimateEndTime != null'>and estimate_end_time &lt; #{estimateEndTime} </if>" +
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
