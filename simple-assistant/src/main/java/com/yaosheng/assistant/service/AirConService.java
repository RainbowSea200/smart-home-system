package com.yaosheng.assistant.service;

import com.yaosheng.assistant.pojo.AirConditioner;

import java.util.List;

public interface AirConService {
    void addAirCon(String name, String image);
    void updateAirCon(AirConditioner airConditioner);
    void deleteAirConById(int id);
    AirConditioner getAirConById(int id);
    List<AirConditioner> getAllAirCon();
    AirConditioner getAirConByName(String name);
}
