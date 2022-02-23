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

import com.lamp.atom.service.operator.entity.CaseEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CaseMapper {
    /**
     * 添加实例
     * @param caseEntity
     */
    @Insert("insert into case" +
            "(case_name,operator_id,operator_name,server_ip," +
            "start_time,end_time,estimate_start_time,estimate_end_time," +
            "status,sequence,reason_data_num,data_flow_num) " +
            "values(#{caseName},#{operatorId},#{operatorName},#{serverIp}," +
            "#{startTime},#{endTime},#{estimateStartTime},#{estimateEndTime}," +
            "#{status},#{sequence},#{reasonDataNum},#{dataFlowNum})")
    Integer insertCaseEntity(CaseEntity caseEntity);

    /**
     * 修改实例
     * @param caseEntity
     * @return
     */
    @Update("update case set " +
            "delete_flag = ${deleteFlag} " +
            "where id = #{id}")
    Integer updateCaseEntity(CaseEntity caseEntity);

    /**
     * 查询多个实例
     * @param caseEntity
     * @return
     */
    @Select({"<script>" +
            "select * from case " +
            "<where>" +
            "<if test = 'operatorId != null'>operator_id = #{operatorId} </if>" +
            "<if test = 'startTime != null'>and start_time &gt; #{startTime} </if>" +
            "<if test = 'endTime != null'>and end_time &lt; #{endTime} </if>" +
            "<if test = 'estimateStartTime != null'>and estimate_start_time &gt; #{estimateStartTime} </if>" +
            "<if test = 'estimateEndTime != null'>and estimate_end_time &lt; #{estimateEndTime} </if>" +
            "</where>" +
            "</script>"})
    List<CaseEntity> queryCaseEntitys(CaseEntity caseEntity);

    /**
     * 查询单个实例
     * @param caseEntity
     * @return
     */
    @Select("select * from case " +
            "where id = #{id} and delete_flag = 0")
    CaseEntity queryCaseEntity(CaseEntity caseEntity);
}
