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
package com.lamp.atom.service.space.provider.mapper;

import com.lamp.atom.service.space.entity.EnvironmentEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface EnvironmentMapper {
    /**
     * 添加环境
     *
     * @param environmentEntity
     */
    @Insert("insert into environment" +
            "(parent_id,environment_type,environment_name,environment_role,label," +
            "create_id,create_name,owner_id,owner_name,owner_type) " +
            "values(#{parentId},#{environmentType},#{environmentName},#{environmentRole},#{label}," +
            "#{createId},#{createName},#{ownerId},#{ownerName},#{ownerType})")
    Integer insertEnvironmentEntity(EnvironmentEntity environmentEntity);

    /**
     * 修改环境
     *
     * @param environmentEntity
     * @return
     */
    @Update("update environment set " +
            "delete_flag = #{deleteFlag} " +
            "where id = #{id}")
    Integer updateEnvironmentEntity(EnvironmentEntity environmentEntity);

    /**
     * 模糊查询多个环境
     *
     * @param keyword
     * @return
     */
    @Select("select * from environment " +
            "where delete_flag = 0 and " +
            "(id like #{keyword} or parent_id like #{keyword} or environment_type like #{keyword} or environment_name like #{keyword} or environment_role like #{keyword} or label like #{keyword} " +
            "or create_id like #{keyword} or create_name like #{keyword} or owner_id like #{keyword} or owner_name like #{keyword} or owner_type like #{keyword})")
    List<EnvironmentEntity> queryEnvironmentEntitysByKeyword(String keyword);

    /**
     * 查询多个环境
     *
     * @param environmentEntity
     * @return
     */
    @Select({"<script>" +
            "select * from environment " +
            "<where>" +
            "<if test = 'parentId != null'>parent_id = #{parentId} </if>" +
            "<if test = 'createId != null'>and create_id = #{createId} </if>" +
            "<if test = 'ownerId != null'>and owner_id = #{ownerId} </if>" +
            "</where>" +
            "</script>"})
    List<EnvironmentEntity> queryEnvironmentEntitys(EnvironmentEntity environmentEntity);

    /**
     * 查询单个环境
     *
     * @param environmentEntity
     * @return
     */
    @Select("select * from environment " +
            "where id = #{id}")
    EnvironmentEntity queryEnvironmentEntity(EnvironmentEntity environmentEntity);
}
