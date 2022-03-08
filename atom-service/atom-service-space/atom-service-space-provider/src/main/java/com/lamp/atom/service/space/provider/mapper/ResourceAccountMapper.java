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

import com.lamp.atom.service.space.entity.ResourceAccountEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ResourceAccountMapper {
    /**
     * 添加资源账户
     *
     * @param resourceAccountEntity
     */
    @Insert("insert into resource_account" +
            "(environment_id,environment_name," +
            "account_type,account_name,password_type,password," +
            "owner_id,owner_name,owner_type,status) " +
            "values(#{environmentId},#{environmentName}," +
            "#{accountType},#{accountName},#{passwordType},#{password}," +
            "#{ownerId},#{ownerName},#{ownerType},#{status})")
    Integer insertResourceAccountEntity(ResourceAccountEntity resourceAccountEntity);

    /**
     * 修改资源账户
     *
     * @param resourceAccountEntity
     * @return
     */
    @Update("update resource_account set " +
            "delete_flag = #{deleteFlag} " +
            "where id = #{id}")
    Integer updateResourceAccountEntity(ResourceAccountEntity resourceAccountEntity);

    /**
     * 模糊查询多个资源账户
     *
     * @param keyword
     * @return
     */
    @Select("select * from resource_account " +
            "where delete_flag = 0 and " +
            "(id like #{keyword} or environment_id like #{keyword} or environment_name like #{keyword} " +
            "or account_type like #{keyword} or account_name like #{keyword} or password_type like #{keyword} or password like #{keyword} " +
            "or owner_id like #{keyword} or owner_name like #{keyword} or owner_type like #{keyword} or status like #{keyword})")
    List<ResourceAccountEntity> queryResourceAccountEntitysByKeyword(String keyword);

    /**
     * 查询多个资源账户
     *
     * @param resourceAccountEntity
     * @return
     */
    @Select({"<script>" +
            "select * from resource_account " +
            "<where>" +
            "<if test = 'environmentId != null'>environment_id = #{environmentId} </if>" +
            "<if test = 'accountType != null'>and accountType = #{accountType} </if>" +
            "<if test = 'passwordType != null'>and password_type = #{passwordType} </if>" +
            "<if test = 'ownerId != null'>and owner_id = #{ownerId} </if>" +
            "<if test = 'ownerType != null'>and owner_type = #{ownerType} </if>" +
            "<if test = 'status != null'>and status = #{status} </if>" +
            "</where>" +
            "</script>"})
    List<ResourceAccountEntity> queryResourceAccountEntitys(ResourceAccountEntity resourceAccountEntity);

    /**
     * 查询单个资源账户
     *
     * @param resourceAccountEntity
     * @return
     */
    @Select("select * from resource_account " +
            "where id = #{id}")
    ResourceAccountEntity queryResourceAccountEntity(ResourceAccountEntity resourceAccountEntity);
}
