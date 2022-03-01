package com.lamp.atom.service.operator.provider.mapper;

import com.lamp.atom.service.operator.entity.AvaliablePortEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AvaliablePortMapper {

    /**
     * 添加可用端口
     * @param avaliablePortEntity
     */
    @Insert("insert into avaliable_port" +
            "(port) " +
            "values(#{port})")
    Integer insertAvaliablePortEntity(AvaliablePortEntity avaliablePortEntity);

    /**
     * 修改可用端口
     * @param avaliablePortEntity
     * @return
     */
    @Update("update avaliable_port set " +
            "delete_flag = #{deleteFlag} " +
            "where id = #{id}")
    Integer updateAvaliablePortEntity(AvaliablePortEntity avaliablePortEntity);

    /**
     * 查询单个可用端口
     * @param avaliablePortEntity
     * @return
     */
    @Select("select * from avaliable_port " +
            "where id = #{id} and delete_flag = 0")
    AvaliablePortEntity queryAvaliablePortEntity(AvaliablePortEntity avaliablePortEntity);
}
