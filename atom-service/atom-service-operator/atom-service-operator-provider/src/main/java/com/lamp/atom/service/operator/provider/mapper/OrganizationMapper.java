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

import com.lamp.atom.service.operator.entity.OrganizationEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface OrganizationMapper {
    /**
     * 添加组织
     *
     * @param organizationEntity
     */
    @Insert("insert into organization" +
            "(parent_id,organization_name,organization_alias,organization_type,explaination," +
            "create_id,create_name,owner_id,owner_name,organization_status) " +
            "values(#{parentId},#{organizationName},#{organizationAlias},#{organizationType},#{explaination}," +
            "#{createId},#{createName},#{ownerId},#{ownerName},#{organizationStatus})")
    Integer insertOrganizationEntity(OrganizationEntity organizationEntity);

    /**
     * 修改组织
     *
     * @param organizationEntity
     * @return
     */
    @Update("update organization set " +
            "delete_flag = #{deleteFlag} " +
            "where id = #{id}")
    Integer updateOrganizationEntity(OrganizationEntity organizationEntity);

    /**
     * 模糊查询多个组织
     *
     * @param keyword
     * @return
     */
    @Select("select * from organization " +
            "where delete_flag = 0 and " +
            "(id like #{keyword} or parent_id like #{keyword} or organization_name like #{keyword} or organization_alias like #{keyword} or organization_type like #{keyword} " +
            "or explaination like #{keyword} or create_id like #{keyword} or create_name like #{keyword} or owner_id like #{keyword} or owner_name like #{keyword} or organization_status like #{keyword})")
    List<OrganizationEntity> queryOrganizationEntitysByKeyword(String keyword);

    /**
     * 查询多个组织
     *
     * @param organizationEntity
     * @return
     */
    @Select({"<script>" +
            "select * from organization " +
            "where delete_flag = 0 " +
            "<if test = 'parentId != null'>and parent_id = #{parentId} </if>" +
            "<if test = 'organizationType != null'>and organization_type = #{organizationType} </if>" +
            "<if test = 'organizationStatus != null'>and organization_status = #{organizationStatus} </if>" +
            "<if test = 'createId != null'>and create_id = #{createId} </if>" +
            "<if test = 'ownerId != null'>and owner_id = #{ownerId} </if>" +
            "</script>"})
    List<OrganizationEntity> queryOrganizationEntitys(OrganizationEntity organizationEntity);

    /**
     * 查询单个组织
     *
     * @param organizationEntity
     * @return
     */
    @Select("select * from organization " +
            "where id = #{id}")
    OrganizationEntity queryOrganizationEntity(OrganizationEntity organizationEntity);

    /**
     * 查询单个组织
     *
     * @param id
     * @return
     */
    @Select("select * from organization " +
            "where id = #{id}")
    OrganizationEntity queryOrganizationById(Long id);

    /**
     * 根据实验查询空间
     *
     * @param organizationEntity
     * @return
     */
    @Select("select * from organization " +
            "where id = (select parent_id from organization " +
            "where id = (select parent_id from organization where id = #{parentId}))")
    OrganizationEntity querySpace(OrganizationEntity organizationEntity);

}
