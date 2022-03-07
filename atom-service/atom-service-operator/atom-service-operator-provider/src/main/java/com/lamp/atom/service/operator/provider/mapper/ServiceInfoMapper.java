package com.lamp.atom.service.operator.provider.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.lamp.atom.service.operator.entity.ServiceInfoEntity;

@Mapper
public interface ServiceInfoMapper {
    /**
     * 添加服务配置
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
     * @param serviceInfoEntity
     * @return
     */
    @Update("update service_info set " +
            "delete_flag = #{deleteFlag} " +
            "where id = #{id}")
    Integer updateServiceInfoEntity(ServiceInfoEntity serviceInfoEntity);

    /**
     * 模糊查询多个服务配置
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
     * @param serviceInfoEntity
     * @return
     */
    @Select({"<script>" +
            "select * from service_info " +
            "<where>" +
            "<if test = 'spaceId != null'>space_id = #{spaceId} </if>" +
            "</where>" +
            "</script>"})
    List<ServiceInfoEntity> queryServiceInfoEntitys(ServiceInfoEntity serviceInfoEntity);

    /**
     * 查询单个服务配置
     * @param serviceInfoEntity
     * @return
     */
    @Select("select * from service_info " +
            "where id = #{id}")
    ServiceInfoEntity queryServiceInfoEntity(ServiceInfoEntity serviceInfoEntity);
}
