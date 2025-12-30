package com.yaosheng.assistant.service;

import com.yaosheng.assistant.pojo.WaterHeater;

import java.util.List;

public interface WaterHeaterService {
    void addWaterHeater(String name, String image);
    void updateWaterHeater(WaterHeater waterHeater);
    void deleteWaterHeaterById(int id);
    WaterHeater getWaterHeaterById(int id);
    List<WaterHeater> getAllWaterHeater();
}
