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
     * @param dataSourceEntity
     * @return
     */
    @Update("update datasource set " +
            "delete_flag = ${deleteFlag} " +
            "where id = #{id}")
    Integer updateDataSourceEntity(DataSourceEntity dataSourceEntity);

    /**
     * 查询多个数据源
     * @param dataSourceEntity
     * @return
     */
    @Select({"<script>" +
            "select * from datasource " +
            "<where>" +
            "<if test = 'operatorId != null'>operator_id = #{operatorId} </if>" +
            "<if test = 'connectionId != null'>and connection_id = #{connectionId} </if>" +
            "</where>" +
            "</script>"})
    List<DataSourceEntity> queryDataSourceEntitys(DataSourceEntity dataSourceEntity);

    /**
     * 查询单个数据源
     * @param dataSourceEntity
     * @return
     */
    @Select("select * from datasource " +
            "where id = #{id} and delete_flag = 0")
    DataSourceEntity queryDataSourceEntity(DataSourceEntity dataSourceEntity);
}