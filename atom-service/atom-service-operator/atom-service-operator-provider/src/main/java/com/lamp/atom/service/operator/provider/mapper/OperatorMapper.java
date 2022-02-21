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
            "(operator_template_id,operator_name,operator_source,operator_source_id,operator_runtime_type,operator_model," +
            "space_id,space_name,space_alias,scene_id,scene_name,scene_alias,experiment_id,experiment_name,experiment_alias," +
            "resources_account_id,code_mode,code_address,code_version,module_name,execute_object,environment_conf,operator_conf,model_conf," +
            "cpu,gpu,men,display_card,operator_epoch,operator_plan_runtimes,operator_status,operator_priority,deploy_type) " +
            "values(#{operatorTemplateId},#{operatorName},#{operatorSourceType},#{operatorSourceId},#{operatorRuntimeType},#{operatorModel}," +
            "#{spaceId},#{spaceName},#{spaceAlias},#{sceneId},#{sceneName},#{sceneAlias},#{experimentId},#{experimentName},#{experimentAlias}," +
            "#{resourcesAccountId},#{codeMode},#{codeAddress},#{codeVersion},#{moduleName},#{executeObject},#{operatorConf},#{environmentConf},#{modelConf}," +
            "#{cpu},#{gpu},#{men},#{displayCard},#{operatorEpoch},#{operatorPlanRuntimes},#{operatorStatus},#{operatorPriority},#{deployType})")
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
            "where id = #{id}" +
            "</set>" +
            "</script>"})
    Integer updateOperatorEntity(OperatorEntity operatorEntity);

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
            "where id = #{id} and delete_flag = 0")
    OperatorEntity queryOperatorEntity(OperatorEntity operatorEntity);
}
