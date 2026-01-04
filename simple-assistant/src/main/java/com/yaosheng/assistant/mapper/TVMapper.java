package com.yaosheng.assistant.mapper;

import com.yaosheng.assistant.pojo.TV;
import org.apache.ibatis.annotations.*;

@Mapper
public interface TVMapper {
    @Insert("insert into tv (name, image) values (#{name},#{image});")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addTV(TV tv);
    @Select("select * from tv where id = #{id}")
    TV getTV(Integer id);
    void updateTV(TV tv);
    @Select("select image from tv where id = #{id}")
    String getImageById(Integer id);
    @Delete("delete from tv where id = #{id}")
    void deleteTVById(Integer id);

    @Select("select * from tv where name = #{name};")
    TV getTVByName(String name);
}
