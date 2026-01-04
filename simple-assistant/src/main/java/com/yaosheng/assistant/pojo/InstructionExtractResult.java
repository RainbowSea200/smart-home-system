package com.yaosheng.assistant.pojo;

import lombok.Data;

@Data
public class InstructionExtractResult {
    /**
     * 操作类型（如：打开空调、关闭空调）
     */
    private Integer deviceType;
    /**
     * 操作类型（如：打开、关闭）
     */
    private Integer action;


    /**
     * 设备名称（如：客厅的空调、卧室的灯光）
     */
    private String name;
}