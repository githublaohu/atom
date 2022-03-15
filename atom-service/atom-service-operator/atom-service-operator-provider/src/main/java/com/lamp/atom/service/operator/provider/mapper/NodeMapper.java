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

import com.lamp.atom.service.operator.entity.NodeEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface NodeMapper {
    /**
     * 添加节点
     *
     * @param nodeEntity
     */
    @Insert("insert into node" +
            "(space_id,node_template_id,node_name,node_type," +
            "node_model,node_epoch,node_plan_runtimes,node_status,operator_priority) " +
            "values(#{spaceId},#{nodeTemplateId},#{nodeName},#{nodeType}," +
            "#{nodeModel},#{nodeEpoch},#{nodePlanRuntimes},#{nodeStatus},#{operatorPriority})")
    Integer insertNodeEntity(NodeEntity nodeEntity);

    /**
     * 修改节点
     *
     * @param nodeEntity
     * @return
     */
    @Update("update node set " +
            "delete_flag = #{deleteFlag} " +
            "where id = #{id}")
    Integer updateNodeEntity(NodeEntity nodeEntity);

    /**
     * 模糊查询多个节点
     *
     * @param keyword
     * @return
     */
    @Select("select * from node " +
            "where delete_flag = 0 and " +
            "(id like #{keyword} or space_id like #{keyword} or node_template_id like #{keyword} or node_name like #{keyword} or node_type like #{keyword} " +
            "or node_model like #{keyword} or node_epoch like #{keyword} or node_plan_runtimes like #{keyword} or node_status like #{keyword} or operator_priority like #{keyword})")
    List<NodeEntity> queryNodeEntitysByKeyword(String keyword);

    /**
     * 查询多个节点
     *
     * @param nodeEntity
     * @return
     */
    @Select({"<script>" +
            "select * from node " +
            "where delete_flag = 0 " +
            "<if test = 'spaceId != null'>and space_id = #{spaceId} </if>" +
            "<if test = 'nodeTemplateId != null'>and node_template_id = #{nodeTemplateId} </if>" +
            "<if test = 'nodeType != null'>and node_type = #{nodeType} </if>" +
            "<if test = 'nodeModel != null'>and node_model = #{nodeModel} </if>" +
            "<if test = 'nodeStatus != null'>and node_status = #{nodeStatus} </if>" +
            "</script>"})
    List<NodeEntity> queryNodeEntitys(NodeEntity nodeEntity);

    /**
     * 查询单个节点
     *
     * @param nodeEntity
     * @return
     */
    @Select("select * from node " +
            "where id = #{id}")
    NodeEntity queryNodeEntity(NodeEntity nodeEntity);
}
