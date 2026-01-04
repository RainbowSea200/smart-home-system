package com.yaosheng.assistant.mapper;

import com.yaosheng.assistant.pojo.Light;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LightMapper {
    @Insert("insert into light (name, image) values (#{name},#{image});")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addLight(Light light);
    @Select("select * from light where id = #{id};")
    Light getLightById(Integer id);
    void updateLight(Light light);
    @Select("select image from light where id = #{id};")
    String getImageById(Integer id);
    @Delete("delete from light where id = #{id};")
    void deleteLightById(Integer id);
    @Select("select * from light where name = #{name};")
    Light getLightByName(String name);
}
