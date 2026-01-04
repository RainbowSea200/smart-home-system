package com.yaosheng.assistant.mapper;

import com.yaosheng.assistant.pojo.AirConditioner;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AirConMapper {
    @Insert("insert into air_conditioner (name, image) values (#{name},#{image});")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addAirCon(AirConditioner airConditioner);
    @Select("select * from air_conditioner where id = #{id};")
    AirConditioner getAirConById(Integer id);
    void updateAirCon(AirConditioner airConditioner);
    @Select("select image from air_conditioner where id = #{id};")
    String getImageById(Integer id);
    @Delete("delete from air_conditioner where id = #{id};")
    void deleteAirConById(Integer id);
    @Select("select id from air_conditioner;")
    List<Integer> getAllAirConIds();
    @Select("select * from air_conditioner where name = #{name};")
    AirConditioner getAirConByName(String name);
}
