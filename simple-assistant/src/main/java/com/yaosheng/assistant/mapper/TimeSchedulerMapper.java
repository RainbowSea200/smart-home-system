package com.yaosheng.assistant.mapper;

import com.yaosheng.assistant.pojo.TimeScheduler;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TimeSchedulerMapper {
    @Insert("insert into time_scheduler (device, time, type) values (#{device},#{time},#{type});")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addTimeScheduler(TimeScheduler timeScheduler);
    @Delete("delete from time_scheduler where id = #{id};")
    void deleteTimeScheduler(Integer id);
    @Update("update time_scheduler set device = #{device}, time = #{time}, type = #{type} where id = #{id};")
    void updateTimeScheduler(TimeScheduler timeScheduler);
    @Select("select * from time_scheduler where type = #{type};")
    List<TimeScheduler> getTimeScheduler(Integer type);
    @Select("select * from time_scheduler;")
    List<TimeScheduler> getAllTimeSchedulers();
}
