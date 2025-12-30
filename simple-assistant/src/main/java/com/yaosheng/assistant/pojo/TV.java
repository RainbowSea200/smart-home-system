package com.yaosheng.assistant.pojo;

import static com.yaosheng.assistant.constant.DeviceConstant.*;
import lombok.Data;

@Data
public class TV {
    private Integer id;
    private String name;
    private Integer statue;
    private String image;
    private Integer channel;

    public TV(String name, String image){
        this.name = name;
        this.image = image;
        this.statue = STATUE_OFF;
        this.channel = TV_CHANNEL_DEFAULT;
    }
}
