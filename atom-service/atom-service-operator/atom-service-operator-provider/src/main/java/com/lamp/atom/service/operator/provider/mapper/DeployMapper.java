package com.lamp.atom.service.operator.provider.mapper;

import com.lamp.atom.service.operator.entity.DeployEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DeployMapper {
    /**
     * 添加部署
     *
     * @param deployEntity
     */
    @Insert("insert into deploy" +
            "(space_id,deploy_template_id,deploy_name,deploy_type," +
            "deploy_model,deploy_epoch,deploy_plan_runtimes,deploy_status,operator_priority) " +
            "values(#{spaceId},#{deployTemplateId},#{deployName},#{deployType}," +
            "#{deployModel},#{deployEpoch},#{deployPlanRuntimes},#{deployStatus},#{operatorPriority})")
    Integer insertDeployEntity(DeployEntity deployEntity);

    /**
     * 修改部署
     *
     * @param deployEntity
     * @return
     */
    @Update("update deploy set " +
            "delete_flag = #{deleteFlag} " +
            "where id = #{id}")
    Integer updateDeployEntity(DeployEntity deployEntity);

    /**
     * 模糊查询多个部署
     *
     * @param keyword
     * @return
     */
    @Select("select * from deploy " +
            "where delete_flag = 0 and " +
            "(id like #{keyword} or space_id like #{keyword} or deploy_template_id like #{keyword} or deploy_name like #{keyword} or deploy_type like #{keyword} " +
            "or deploy_model like #{keyword} or deploy_epoch like #{keyword} or deploy_plan_runtimes like #{keyword} or deploy_status like #{keyword} or operator_priority like #{keyword})")
    List<DeployEntity> queryDeployEntitysByKeyword(String keyword);

    /**
     * 查询多个部署
     *
     * @param deployEntity
     * @return
     */
    @Select({"<script>" +
            "select * from deploy " +
            "where delete_flag = 0 " +
            "<if test = 'spaceId != null'>and space_id = #{spaceId} </if>" +
            "<if test = 'deployTemplateId != null'>and deploy_template_id = #{deployTemplateId} </if>" +
            "<if test = 'deployType != null'>and deploy_type = #{deployType} </if>" +
            "<if test = 'deployModel != null'>and deploy_model = #{deployModel} </if>" +
            "<if test = 'deployStatus != null'>and deploy_status = #{deployStatus} </if>" +
            "</script>"})
    List<DeployEntity> queryDeployEntitys(DeployEntity deployEntity);

    /**
     * 查询单个部署
     *
     * @param id
     * @return
     */
    @Select("select * from deploy " +
            "where id = #{id}")
    DeployEntity queryDeployEntity(Long id);
}
