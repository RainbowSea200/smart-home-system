package com.yaosheng.assistant.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeScheduler {
    private Integer id;
    private String device;
    private String time;
    private Integer type;
}
