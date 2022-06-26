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
     *
     * @param operatorEntity
     */
    @Insert("insert into operator" +
            "(space_id,operator_template_id,operator_name,operator_source_id,operator_source_type,operator_runtime_type,operator_model," +
            "level,resources_account_id,code_mode,code_address,code_version,module_name,execute_object,environment_conf,operator_conf,model_conf," +
            "operator_epoch,operator_priority,deploy_type) " +
            "values(#{spaceId},#{operatorTemplateId},#{operatorName},#{operatorSourceId},#{operatorSourceType},#{operatorRuntimeType},#{operatorModel}," +
            "#{level},#{resourcesAccountId},#{codeMode},#{codeAddress},#{codeVersion},#{moduleName},#{executeObject},#{operatorConf},#{environmentConf},#{modelConf}," +
            "#{operatorEpoch},#{operatorPriority},#{deployType})")
    Integer insertOperatorEntity(OperatorEntity operatorEntity);

    /**
     * 修改算子
     *
     * @param operatorEntity
     * @return
     */
    @Update({"<script>" +
            "update operator" +
            "<set>" +
            "<if test = 'operatorRuntimeStatus != null'>operator_runtime_status = #{operatorRuntimeStatus},</if>" +
            "<if test = 'deleteFlag != null'>delete_flag = #{deleteFlag}</if>" +
            "</set>" +
            "where id = #{id}" +
            "</script>"})
    Integer updateOperatorEntity(OperatorEntity operatorEntity);

    /**
     * 模糊查询多个算子
     *
     * @param keyword
     * @return
     */
    @Select("select * from operator " +
            "where delete_flag = 0 and " +
            "(id like #{keyword} or space_id like #{keyword} or operator_template_id like #{keyword} or operator_name like #{keyword} or operator_source_id like #{keyword} " +
            "or operator_source_type like #{keyword} or operator_runtime_type like #{keyword} or operator_model like #{keyword} " +
            "or level like #{keyword} or resources_account_id like #{keyword} or code_mode like #{keyword} or code_address like #{keyword} " +
            "or code_version like #{keyword} or module_name like #{keyword} or execute_object like #{keyword} " +
            "or operator_conf like #{keyword} or environment_conf like #{keyword} or model_conf like #{keyword} " +
            "or operator_epoch like #{keyword} or operator_plan_runtimes like #{keyword} " +
            "or operator_runtime_status like #{keyword} or operator_priority like #{keyword} or deploy_type like #{keyword})")
    List<OperatorEntity> queryOperatorEntitysByKeyword(String keyword);

    /**
     * 查询多个算子
     *
     * @param operatorEntity
     * @return
     */
    @Select({"<script>" +
            "select * from operator " +
            "where delete_flag = 0 " +
            "<if test = 'spaceId != null'>and space_id = #{spaceId} </if>" +
            "<if test = 'operatorTemplateId != null'>and operator_template_id = #{operatorTemplateId} </if>" +
            "<if test = 'operatorSourceId != null'>and operator_source_id = #{operatorSourceId} </if>" +
            "<if test = 'operatorSourceType != null'>and operator_source_type = #{operatorSourceType} </if>" +
            "<if test = 'operatorRuntimeType != null'>and operator_runtime_type = #{operatorRuntimeType} </if>" +
            "<if test = 'operatorModel != null'>and operator_model = #{operatorModel} </if>" +
            "<if test = 'level != null'>and level = #{level} </if>" +
            "<if test = 'resourcesAccountId != null'>and resources_account_id = #{resourcesAccountId} </if>" +
            "<if test = 'codeMode != null'>and code_mode = #{codeMode} </if>" +
            "<if test = 'codeAddress != null'>and code_address = #{codeAddress} </if>" +
            "<if test = 'codeVersion != null'>and code_version = #{codeVersion} </if>" +
            "<if test = 'operatorEpoch != null'>and operator_epoch = #{operatorEpoch} </if>" +
            "<if test = 'operatorPriority != null'>and operator_priority = #{operatorPriority} </if>" +
            "<if test = 'deployType != null'>and deploy_type = #{deployType} </if>" +
            "</script>"})
    List<OperatorEntity> queryOperatorEntitys(OperatorEntity operatorEntity);

    /**
     * 查询单个算子
     *
     * @param operatorEntity
     * @return
     */
    @Select("select * from operator " +
            "where id = #{id}")
    OperatorEntity queryOperatorEntity(OperatorEntity operatorEntity);
}
