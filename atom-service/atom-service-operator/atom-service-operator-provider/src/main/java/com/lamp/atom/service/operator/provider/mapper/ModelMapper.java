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

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.lamp.atom.service.operator.entity.ModelEntity;

@Mapper
public interface ModelMapper {
    /**
     * 添加模型
     * @param modelEntity
     */
    @Insert("insert into model" +
            "(space_id,node_id,runtime_id,operator_id," +
            "model_create_type,model_name,model_version,model_score,model_type,model_technology_type,model_address,model_status," +
            "connect_id,connect_status,operator_result,resource_type,resource_value,resource_size,produce_way) " +
            "values(#{spaceId},#{nodeId},#{runtimeId},#{operatorId}," +
            "#{modelCreateType},#{modelName},#{modelVersion},#{modelScore},#{modelType},#{modelTechnologyType},#{modelAddress},#{modelStatus}," +
            "#{connectId},#{connectStatus},#{operatorResult},#{resourceType},#{resourceValue},#{resourceSize},#{produceWay})")
    Integer insertModelEntity(ModelEntity modelEntity);

    /**
     * 修改模型
     * @param modelEntity
     * @return
     */
    @Update("update model set " +
            "delete_flag = #{deleteFlag} " +
            "where id = #{id}")
    Integer updateModelEntity(ModelEntity modelEntity);

    /**
     * 模糊查询多个模型
     * @param keyword
     * @return
     */
    @Select("select * from model " +
            "where delete_flag = 0 and " +
            "(id like #{keyword} or space_id like #{keyword} or space_name like #{keyword} or space_alias like #{keyword} " +
            "or scene_id like #{keyword} or scene_name like #{keyword} or scene_alias like #{keyword} " +
            "or experiment_id like #{keyword} or experiment_name like #{keyword} or experiment_alias like #{keyword} " +
            "or model_create_type like #{keyword} or model_name like #{keyword} or model_version like #{keyword} " +
            "or model_score like #{keyword} or model_type like #{keyword} or model_technology_type like #{keyword} " +
            "or model_address like #{keyword} or model_status like #{keyword} or connect_id like #{keyword} " +
            "or connect_status like #{keyword} or operator_id like #{keyword} or operator_result like #{keyword} " +
            "or resource_type like #{keyword} or resource_value like #{keyword} or resource_size like #{keyword} or produce_way like #{keyword})")
    List<ModelEntity> queryModelEntitysByKeyword(String keyword);

    /**
     * 查询多个模型
     * @param modelEntity
     * @return
     */
    @Select({"<script>" +
            "select * from model " +
            "<where>" +
            "<if test = 'spaceId != null'>space_id = #{spaceId} </if>" +
            "<if test = 'sceneId != null'>and scene_id = #{sceneId} </if>" +
            "<if test = 'experimentId != null'>and experiment_id = #{experimentId} </if>" +
            "<if test = 'connectId != null'>and connect_id = #{connectId} </if>" +
            "<if test = 'operatorId != null'>and operator_id = #{operatorId} </if>" +
            "</where>" +
            "</script>"})
    List<ModelEntity> queryModelEntitys(ModelEntity modelEntity);

    /**
     * 查询单个模型
     * @param modelEntity
     * @return
     */
    @Select("select * from model " +
            "where id = #{id}")
    ModelEntity queryModelEntity(ModelEntity modelEntity);
}
