package com.yaosheng.assistant.pojo;

import static com.yaosheng.assistant.constant.DeviceConstant.*;
import lombok.Data;

@Data
public class Light {
    private Integer id;
    private String name;
    private Integer statue;
    private String image;
    private Integer brightness;

    public Light(String name, String image){
        this.name = name;
        this.image = image;
        this.statue = STATUE_OFF;
        this.brightness = LIGHT_BRIGHTNESS_DEFAULT;
    }
}
