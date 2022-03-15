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

import com.lamp.atom.service.operator.entity.ConnectionEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ConnectionMapper {
    /**
     * 添加连接
     *
     * @param connectionEntity
     */
    @Insert("insert into connection" +
            "(space_id,operation_type,source_type,source_name,source_addr,source_account,source_password,source_space," +
            "mode,colony_type,source_conf,source_route,source_size,source_count) " +
            "values(#{spaceId},#{operationType},#{sourceType},#{sourceName},#{sourceAddr},#{sourceAccount},#{sourcePassword},#{sourceSpace}," +
            "#{mode},#{colonyType},#{sourceConf},#{sourceRoute},#{sourceSize},#{sourceCount})")
    Integer insertConnectionEntity(ConnectionEntity connectionEntity);

    /**
     * 修改连接
     *
     * @param connectionEntity
     * @return
     */
    @Update("update connection set " +
            "delete_flag = #{deleteFlag} " +
            "where id = #{id}")
    Integer updateConnectionEntity(ConnectionEntity connectionEntity);

    /**
     * 模糊查询多个连接
     *
     * @param keyword
     * @return
     */
    @Select("select * from connection " +
            "where delete_flag = 0 and " +
            "(id like #{keyword} or space_id like #{keyword} or operation_type like #{keyword} " +
            "or source_type like #{keyword} or source_name like #{keyword} or source_addr like #{keyword} " +
            "or source_account like #{keyword} or source_password like #{keyword} or source_space like #{keyword} " +
            "or mode like #{keyword} or colony_type like #{keyword} " +
            "or source_conf like #{keyword} or source_route like #{keyword} or source_size like #{keyword} or source_count like #{keyword})")
    List<ConnectionEntity> queryConnectionEntitysByKeyword(String keyword);

    /**
     * 查询多个连接
     * 查询未被删除的
     *
     * @return
     */
    @Select({"<script>" +
            "select * from connection " +
            "where delete_flag = 0 " +
            "<if test = 'spaceId != null'>and space_id = #{spaceId} </if>" +
            "<if test = 'sourceType != null'>and source_type = #{sourceType} </if>" +
            "<if test = 'sourceAddr != null'>and source_addr = #{sourceAddr} </if>" +
            "<if test = 'sourceAccount != null'>and source_account = #{sourceAccount} </if>" +
            "<if test = 'sourceSpace != null'>and source_space = #{sourceSpace} </if>" +
            "<if test = 'mode != null'>and mode = #{mode} </if>" +
            "<if test = 'colonyType != null'>and colony_type = #{colonyType} </if>" +
            "</script>"})
    List<ConnectionEntity> queryConnectionEntitys(ConnectionEntity connectionEntity);

    /**
     * 查询单个连接
     *
     * @param connectionEntity
     * @return
     */
    @Select("select * from connection " +
            "where id = #{id}")
    ConnectionEntity queryConnectionEntity(ConnectionEntity connectionEntity);
}
