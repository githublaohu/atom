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

import com.lamp.atom.service.operator.entity.ServiceInfoEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ServiceInfoMapper {
    /**
     * 添加服务配置
     *
     * @param serviceInfoEntity
     */
    @Insert("insert into service_info" +
            "(space_id,si_name,si_type,si_runtime_pattern,si_node_num,si_image_name," +
            "si_cpu,si_gpu,si_memory,si_display_memory,si_label) " +
            "values(#{spaceId},#{siName},#{siType},#{siRuntimePattern},#{siNodeNum},#{siImageName}," +
            "#{siCpu},#{siGpu},#{siMemory},#{siDisplayMemory},#{siLabel})")
    Integer insertServiceInfoEntity(ServiceInfoEntity serviceInfoEntity);

    /**
     * 修改服务配置
     *
     * @param serviceInfoEntity
     * @return
     */
    @Update("update service_info set " +
            "delete_flag = #{deleteFlag} " +
            "where id = #{id}")
    Integer updateServiceInfoEntity(ServiceInfoEntity serviceInfoEntity);

    /**
     * 模糊查询多个服务配置
     *
     * @param keyword
     * @return
     */
    @Select("select * from service_info " +
            "where delete_flag = 0 and " +
            "(id like #{keyword} or space_id like #{keyword} or si_name like #{keyword} or si_type like #{keyword} " +
            "or si_runtime_pattern like #{keyword} or si_node_num like #{keyword} or si_image_name like #{keyword} " +
            "or si_cpu like #{keyword} or si_gpu like #{keyword} or si_memory like #{keyword} or si_display_memory like #{keyword} or si_label like #{keyword})")
    List<ServiceInfoEntity> queryServiceInfoEntitysByKeyword(String keyword);

    /**
     * 查询多个服务配置
     *
     * @param serviceInfoEntity
     * @return
     */
    @Select({"<script>" +
            "select * from service_info " +
            "where delete_flag = 0 " +
            "<if test = 'spaceId != null'>and space_id = #{spaceId} </if>" +
            "<if test = 'siRuntimePattern != null'>and si_runtime_pattern = #{siRuntimePattern} </if>" +
            "<if test = 'siImageName != null'>and si_image_name = #{siImageName} </if>" +
            "<if test = 'siLabel != null'>and si_label = #{siLabel} </if>" +
            "</script>"})
    List<ServiceInfoEntity> queryServiceInfoEntitys(ServiceInfoEntity serviceInfoEntity);

    /**
     * 查询单个服务配置
     *
     * @param serviceInfoEntity
     * @return
     */
    @Select("select * from service_info " +
            "where id = #{id}")
    ServiceInfoEntity queryServiceInfoEntity(ServiceInfoEntity serviceInfoEntity);
}
