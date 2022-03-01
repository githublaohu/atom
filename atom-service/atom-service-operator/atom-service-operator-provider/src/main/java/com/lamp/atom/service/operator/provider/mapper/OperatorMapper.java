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

import com.lamp.atom.service.operator.entity.OperatorEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface OperatorMapper {
    /**
     * 添加算子
     * @param operatorEntity
     */
    @Insert("insert into operator" +
            "(operator_template_id,operator_name,operator_source_id,operator_source_type,operator_runtime_type,operator_model," +
            "space_id,space_name,space_alias,scene_id,scene_name,scene_alias,experiment_id,experiment_name,experiment_alias," +
            "level,resources_account_id,code_mode,code_address,code_version,module_name,execute_object,environment_conf,operator_conf,model_conf," +
            "cpu,gpu,men,display_card," +
            "operator_epoch,operator_plan_runtimes,operator_status,operator_priority,case_id,deploy_type) " +
            "values(#{operatorTemplateId},#{operatorName},#{operatorSourceId},#{operatorSourceType},#{operatorRuntimeType},#{operatorModel}," +
            "#{spaceId},#{spaceName},#{spaceAlias},#{sceneId},#{sceneName},#{sceneAlias},#{experimentId},#{experimentName},#{experimentAlias}," +
            "#{level},#{resourcesAccountId},#{codeMode},#{codeAddress},#{codeVersion},#{moduleName},#{executeObject},#{operatorConf},#{environmentConf},#{modelConf}," +
            "#{cpu},#{gpu},#{men},#{displayCard}," +
            "#{operatorEpoch},#{operatorPlanRuntimes},#{operatorStatus},#{operatorPriority},#{caseId},#{deployType})")
    Integer insertOperatorEntity(OperatorEntity operatorEntity);

    /**
     * 修改算子
     * @param operatorEntity
     * @return
     */
    @Update({"<script>" +
            "update operator" +
            "<set>" +
            "<if test = 'operatorStatus != null'>operator_status = #{operatorStatus},</if>" +
            "<if test = 'deleteFlag != null'>delete_flag = #{deleteFlag}</if>" +
            "</set>" +
            "where id = #{id}" +
            "</script>"})
    Integer updateOperatorEntity(OperatorEntity operatorEntity);

    /**
     * 模糊查询多个算子
     * @param keyword
     * @return
     */
    @Select("select * from operator " +
            "where delete_flag = 0 and " +
            "(id like #{keyword} or operator_template_id like #{keyword} or operator_name like #{keyword} or operator_source_id like #{keyword} " +
            "or operator_source_type like #{keyword} or operator_runtime_type like #{keyword} or operator_model like #{keyword} " +
            "or space_id like #{keyword} or space_name like #{keyword} or space_alias like #{keyword} " +
            "or scene_id like #{keyword} or scene_name like #{keyword} or scene_alias like #{keyword} " +
            "or experiment_id like #{keyword} or experiment_name like #{keyword} or experiment_alias like #{keyword} " +
            "or level like #{keyword} or resources_account_id like #{keyword} or code_mode like #{keyword} or code_address like #{keyword} " +
            "or code_version like #{keyword} or module_name like #{keyword} or execute_object like #{keyword} " +
            "or operator_conf like #{keyword} or environment_conf like #{keyword} or model_conf like #{keyword} " +
            "or cpu like #{keyword} or gpu like #{keyword} or men like #{keyword} or display_card like #{keyword} " +
            "or operator_epoch like #{keyword} or operator_plan_runtimes like #{keyword} " +
            "or operator_status like #{keyword} or operator_priority like #{keyword} or case_id like #{keyword} or deploy_type like #{keyword})")
    List<OperatorEntity> queryOperatorEntitysByKeyword(String keyword);

    /**
     * 查询多个算子
     * @param operatorEntity
     * @return
     */
    @Select({"<script>" +
            "select * from operator " +
            "<where>" +
            "<if test = 'spaceId != null'>space_id = #{spaceId} </if>" +
            "<if test = 'sceneId != null'>and scene_id = #{sceneId} </if>" +
            "<if test = 'experimentId != null'>and experiment_id = #{experimentId} </if>" +
            "<if test = 'resourcesAccountId != null'>and resources_account_id = #{resourcesAccountId} </if>" +
            "<if test = 'operatorEpoch != null'>and operator_epoch = #{operatorEpoch} </if>" +
            "<if test = 'operatorPriority != null'>and operator_priority = #{operatorPriority} </if>" +
            "</where>" +
            "</script>"})
    List<OperatorEntity> queryOperatorEntitys(OperatorEntity operatorEntity);

    /**
     * 查询单个算子
     * @param operatorEntity
     * @return
     */
    @Select("select * from operator " +
            "where id = #{id}")
    OperatorEntity queryOperatorEntity(OperatorEntity operatorEntity);
}
