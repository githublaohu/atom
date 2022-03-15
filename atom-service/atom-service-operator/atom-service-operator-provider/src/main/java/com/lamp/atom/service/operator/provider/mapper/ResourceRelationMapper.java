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
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ResourceRelationMapper {
    /**
     * 添加资源关系
     *
     * @param resourceRelationEntity
     */
    @Insert("insert into resource_relation" +
            "(relation_id,relation_type,be_relation_id,be_relation_type," +
            "relation_status,`order`) " +
            "values(#{relationId},#{relationType},#{beRelationId},#{beRelationType}," +
            "#{relationStatus},#{order})")
    Integer insertResourceRelationEntity(ResourceRelationEntity resourceRelationEntity);

    /**
     * 修改资源关系
     *
     * @param resourceRelationEntity
     * @return
     */
    @Update("update resource_relation set " +
            "delete_flag = #{deleteFlag} " +
            "where id = #{id}")
    Integer updateResourceRelationEntity(ResourceRelationEntity resourceRelationEntity);

    /**
     * 模糊查询多个资源关系
     *
     * @param keyword
     * @return
     */
    @Select("select * from resource_relation " +
            "where delete_flag = 0 and " +
            "(id like #{keyword} or relation_id like #{keyword} or relation_type like #{keyword} or be_relation_id like #{keyword} or be_relation_type like #{keyword} " +
            "or relation_status like #{keyword} or 'order' like #{keyword})")
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
            "<if test = 'relationId != null'>and relation_id = #{relationId} </if>" +
            "<if test = 'relationType != null'>and relation_type = #{relationType} </if>" +
            "<if test = 'beRelationId != null'>and be_relation_id = #{beRelationId} </if>" +
            "<if test = 'beRelationType != null'>and be_relation_type = #{beRelationType} </if>" +
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
