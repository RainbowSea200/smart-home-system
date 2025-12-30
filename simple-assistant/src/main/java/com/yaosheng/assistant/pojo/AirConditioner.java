package com.yaosheng.assistant.pojo;

import lombok.Data;
import static com.yaosheng.assistant.constant.DeviceConstant.*;

@Data
public class AirConditioner {
    private Integer id;
    private String name;
    private Integer statue;
    private String image;
    private Integer pattern;
    private Integer temperature;
    private Integer swing;

    public AirConditioner(String name, String image){
        this.name = name;
        this.image = image;
        this.statue = 0;
        this.pattern = AIR_CON_PATTERN_AUTO;
        this.temperature = 25;
        this.swing = AIR_CON_SWING_OFF;
    }
}
