package com.yaosheng.assistant.service;

import com.yaosheng.assistant.pojo.Light;

import java.util.List;

public interface LightService {

    void addLight(String name, String image);
    void updateLight(Light light);
    void deleteLightById(int id);
    Light getLightById(int id);
    List<Light> getAllLight();
    Light getLightByName(String name);
}
