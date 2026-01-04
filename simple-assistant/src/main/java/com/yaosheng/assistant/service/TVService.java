package com.yaosheng.assistant.service;

import com.yaosheng.assistant.pojo.TV;

import java.util.List;

public interface TVService {
    void addTV(String name, String image);
    void updateTV(TV tv);
    void deleteTVById(int id);
    TV getTVById(int id);
    List<TV> getAllTV();
    TV getTVByName(String name);
}
