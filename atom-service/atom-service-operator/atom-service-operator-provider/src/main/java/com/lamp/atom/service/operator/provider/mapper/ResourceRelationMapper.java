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

import com.lamp.atom.service.operator.entity.ResourceRelationEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ResourceRelationMapper {
    /**
     * 添加资源关系
     *
     * @param resourceRelationEntity
     */
    @Insert("insert into resource_relation" +
            "(relate_id,relate_type,be_related_id,be_related_type," +
            "relation_type,relation_status,`order`) " +
            "values(#{relateId},#{relateType},#{beRelatedId},#{beRelatedType}," +
            "#{relationType},#{relationStatus},#{order})")
    Integer insertResourceRelationEntity(ResourceRelationEntity resourceRelationEntity);

    /**
     * 批量添加资源关系
     *
     * @param resourceRelationEntityList
     */
    @Insert("<script>" +
            "insert into resource_relation" +
            "(relate_id,relate_type,be_related_id,be_related_type," +
            "relation_type,relation_status,`order`) " +
            "values " +
            "<foreach collection='resourceRelationEntityList' item='item' index='index' separator=','>" +
            "(#{item.relateId},#{item.relateType},#{item.beRelatedId},#{item.beRelatedType}," +
            "#{item.relationType},#{item.relationStatus},#{item.order})" +
            "</foreach>" +
            "</script>")
    Integer batchInsertResourceRelationEntity(@Param("resourceRelationEntityList") List<ResourceRelationEntity> resourceRelationEntityList);

    /**
     * 修改资源关系
     *
     * @param resourceRelationEntity
     * @return
     */
    @Update({"<script>" +
            "update resource_relation " +
            "set " +
            "<if test = 'beRelatedId != null'>be_related_id = #{beRelatedId},</if>" +
            "<if test = 'relationType != null'>relation_type = #{relationType},</if>" +
            "<if test = 'relationStatus != null'>relation_status = #{relationStatus},</if>" +
            "<if test = 'order != null'>resource_relation.order = #{order} </if>" +
            "where delete_flag = 0 and " +
            "relate_id = #{relateId} and relate_type = #{relateType} and be_related_type = #{beRelatedType}" +
            "</script>"})
    Integer updateResourceRelationEntity(ResourceRelationEntity resourceRelationEntity);

    /**
     * 模糊查询多个资源关系
     *
     * @param keyword
     * @return
     */
    @Select("select * from resource_relation " +
            "where delete_flag = 0 and " +
            "(id like #{keyword} or relate_id like #{keyword} or relate_type like #{keyword} " +
            "or be_related_id like #{keyword} or be_related_type like #{keyword} " +
            "or relation_type like {keyword} or relation_status like #{keyword} or 'order' like #{keyword})")
    List<ResourceRelationEntity> queryResourceRelationEntitysByKeyword(String keyword);

    /**
     * 查询多个资源关系
     *
     * @param resourceRelationEntity
     * @return
     */
    @Select({"<script>" +
            "select * from resource_relation " +
            "where delete_flag = 0" +
            "<if test = 'relateId != null'>and relate_id = #{relateId} </if>" +
            "<if test = 'relateType != null'>and relate_type = #{relateType} </if>" +
            "<if test = 'beRelatedId != null'>and be_related_id = #{beRelatedId} </if>" +
            "<if test = 'beRelatedType != null'>and be_related_type = #{beRelatedType} </if>" +
            "<if test = 'relationType != null'>and relation_type = #{relationType} </if>" +
            "<if test = 'relationStatus != null'>and relation_status = #{relationStatus} </if>" +
            "<if test = 'order != null'>and 'order' = #{order} </if>" +
            "</script>"})
    List<ResourceRelationEntity> queryResourceRelationEntitys(ResourceRelationEntity resourceRelationEntity);

    /**
     * 查询单个资源关系
     *
     * @param resourceRelationEntity
     * @return
     */
    @Select("select * from resource_relation " +
            "where id = #{id}")
    ResourceRelationEntity queryResourceRelationEntity(ResourceRelationEntity resourceRelationEntity);
}
