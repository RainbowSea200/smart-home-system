package com.yaosheng.assistant.mapper;

import com.yaosheng.assistant.pojo.WaterHeater;
import org.apache.ibatis.annotations.*;

@Mapper
public interface WaterHeaterMapper {
    @Insert("insert into water_heater (name, image) values (#{name},#{image});")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addWaterHeater(WaterHeater waterHeater);
    @Select("select * from water_heater where id = #{id};")
    WaterHeater getWaterHeaterById(Integer id);
    void updateWaterHeater(WaterHeater waterHeater);
    @Select("select image from water_heater where id = #{id};")
    String getImageById(Integer id);
    @Delete("delete from water_heater where id = #{id};")
    void deleteWaterHeaterById(Integer id);
    @Select("select * from water_heater where name = #{name};")
    WaterHeater getWaterHeaterByName(String name);
}
