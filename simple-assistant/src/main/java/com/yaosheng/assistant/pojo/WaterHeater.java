package com.yaosheng.assistant.pojo;

import static com.yaosheng.assistant.constant.DeviceConstant.*;
import lombok.Data;

@Data
public class WaterHeater {
    private Integer id;
    private String name;
    private Integer statue;
    private String image;
    private Integer temperature;
    private Integer isFull;

    public WaterHeater(String name, String image){
        this.name = name;
        this.image = image;
        this.statue = STATUE_OFF;
        this.temperature = WATER_HEATER_TEMPERATURE_DEFAULT;
        this.isFull = WATER_HEATER_NOT_EMPTY;
    }
}
