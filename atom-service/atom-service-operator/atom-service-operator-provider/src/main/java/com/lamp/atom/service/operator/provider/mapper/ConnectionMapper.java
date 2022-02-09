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
     * @param connectionEntity
     */
    @Insert("insert into connection" +
            "(space_id,space_name,space_alias,scene_id,scene_name,scene_alias,experiment_id,experiment_name,experiment_alias," +
            "operation_type,source_type,source_name,source_addr,source_account,source_password,source_space," +
            "mode,colony_type,source_conf,source_route,source_size,source_count) " +
            "values(#{spaceId},#{spaceName},#{spaceAlias},#{sceneId},#{sceneName},#{sceneAlias},#{experimentId},#{experimentName},#{experimentAlias}," +
            "#{operationType},#{sourceType},#{sourceName},#{sourceAddr},#{sourceAccount},#{sourcePassword},#{sourceSpace}," +
            "#{mode},#{colonyType},#{sourceConf},#{sourceRoute},#{sourceSize},#{sourceCount})")
    Integer insertConnectionEntity(ConnectionEntity connectionEntity);

    /**
     * 修改连接
     * @param connectionEntity
     * @return
     */
    @Update("update connection set " +
            "delete_flag = ${deleteFlag} " +
            "where id = #{id}")
    Integer updateConnectionEntity(ConnectionEntity connectionEntity);

    /**
     * 查询多个连接
     * @param connectionEntity
     * @return
     */
    @Select({"<script>" +
            "select * from connection " +
            "<where>" +
            "<if test = 'spaceId != null'>space_id = #{spaceId} </if>" +
            "<if test = 'sceneId != null'>and scene_id = #{sceneId} </if>" +
            "<if test = 'experimentId != null'>and experiment_id = #{experimentId} </if>" +
            "<if test = 'colonyType != null'>and colony_type = #{colonyType} </if>" +
            "</where>" +
            "</script>"})
    List<ConnectionEntity> queryConnectionEntitys(ConnectionEntity connectionEntity);

    /**
     * 查询单个连接
     * @param connectionEntity
     * @return
     */
    @Select("select * from connection " +
            "where id = #{id} and delete_flag = 0")
    ConnectionEntity queryConnectionEntity(ConnectionEntity connectionEntity);
}
