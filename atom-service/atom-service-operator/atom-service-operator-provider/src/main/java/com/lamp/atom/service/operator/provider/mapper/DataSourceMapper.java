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

import com.lamp.atom.service.operator.entity.DataSourceEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DataSourceMapper {
    /**
     * 添加数据源
     *
     * @param dataSourceEntity
     */
    @Insert("insert into datasource" +
            "(operator_id,connection_id,data_source_type,source_space,source_conf," +
            "task_init_execute,operate_execute_before,data_execute_before,operate_execute,data_execute_after,operate_execute_after," +
            "data_format,operator_read_num,connect_read_num,disposable,paginate_read_num,async_load,`order`) " +
            "values(#{operatorId},#{connectionId},#{dataSourceType},#{sourceSpace},#{sourceConf}," +
            "#{taskInitExecute},#{operateExecuteBefore},#{dataExecuteBefore},#{operateExecute},#{dataExecuteAfter},#{operateExecuteAfter}," +
            "#{dataFormat},#{operatorReadNum},#{connectReadNum},#{disposable},#{paginateReadNum},#{asyncLoad},#{order})")
    Integer insertDataSourceEntity(DataSourceEntity dataSourceEntity);

    /**
     * 修改数据源
     *
     * @param dataSourceEntity
     * @return
     */
    @Update("update datasource set " +
            "delete_flag = #{deleteFlag} " +
            "where id = #{id}")
    Integer updateDataSourceEntity(DataSourceEntity dataSourceEntity);

    /**
     * 模糊查询多个数据源
     *
     * @param keyword
     * @return
     */
    @Select("select * from datasource " +
            "where delete_flag = 0 and " +
            "(id like #{keyword} or operator_id like #{keyword} or connection_id like #{keyword} or data_source_type like #{keyword} " +
            "or source_space like #{keyword} or source_conf like #{keyword} or task_init_execute like #{keyword} " +
            "or operate_execute_before like #{keyword} or data_execute_before like #{keyword} or operate_execute like #{keyword} " +
            "or data_execute_after like #{keyword} or operate_execute_after like #{keyword} or data_format like #{keyword} " +
            "or operator_read_num like #{keyword} or connect_read_num like #{keyword} or disposable like #{keyword} " +
            "or paginate_read_num like #{keyword} or async_load like #{keyword} or `order` like #{keyword})")
    List<DataSourceEntity> queryDataSourceEntitysByKeyword(String keyword);

    /**
     * 查询多个数据源
     *
     * @param dataSourceEntity
     * @return
     */
    @Select({"<script>" +
            "select * from datasource " +
            "where delete_flag = 0 " +
            "<if test = 'operatorId != null'>and operator_id = #{operatorId} </if>" +
            "<if test = 'connectionId != null'>and connection_id = #{connectionId} </if>" +
            "<if test = 'dataSourceType != null'>and dataSource_type = #{dataSourceType} </if>" +
            "<if test = 'sourceSpace != null'>and source_space = #{sourceSpace} </if>" +
            "<if test = 'disposable != null'>and disposable = #{disposable} </if>" +
            "<if test = 'asyncLoad != null'>and async_load = #{asyncLoad} </if>" +
            "<if test = 'order != null'>and 'order' = #{order} </if>" +
            "</script>"})
    List<DataSourceEntity> queryDataSourceEntitys(DataSourceEntity dataSourceEntity);

    /**
     * 查询单个数据源
     *
     * @param dataSourceEntity
     * @return
     */
    @Select("select * from datasource " +
            "where id = #{id}")
    DataSourceEntity queryDataSourceEntity(DataSourceEntity dataSourceEntity);
}
