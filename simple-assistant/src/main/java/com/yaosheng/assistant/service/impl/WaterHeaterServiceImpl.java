package com.yaosheng.assistant.service.impl;

import com.yaosheng.assistant.mapper.WaterHeaterMapper;
import com.yaosheng.assistant.mapper.redis.RedisMapper;
import com.yaosheng.assistant.pojo.WaterHeater;
import com.yaosheng.assistant.service.WaterHeaterService;
import com.yaosheng.assistant.util.LocalFileStorageUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.yaosheng.assistant.constant.RedisConstant.*;

@Service
public class WaterHeaterServiceImpl implements WaterHeaterService {
    @Resource
    private RedisMapper redisMapper;
    @Resource
    private WaterHeaterMapper waterHeaterMapper;
    @Resource
    private LocalFileStorageUtil util;

    @Override
    @Transactional
    public void addWaterHeater(String name, String image) {
        WaterHeater waterHeater = new WaterHeater(name, image);
        waterHeaterMapper.addWaterHeater(waterHeater);
        redisMapper.addDevice(DEVICE_WATER_HEATER, waterHeater.getId(), waterHeater);
    }

    @Override
    @Transactional
    public void updateWaterHeater(WaterHeater waterHeater) {
        waterHeaterMapper.updateWaterHeater(waterHeater);
        redisMapper.updateDevice(DEVICE_WATER_HEATER, waterHeater.getId(), waterHeater);
    }

    @Override
    @Transactional
    public void deleteWaterHeaterById(int id) {
        util.deleteFile(getWaterHeaterById(id).getImage());
        waterHeaterMapper.deleteWaterHeaterById(id);
        redisMapper.deleteDevice(DEVICE_WATER_HEATER, id);
    }

    @Override
    public WaterHeater getWaterHeaterById(int id) {
        return redisMapper.getDevice(DEVICE_WATER_HEATER, id, WaterHeater.class);
    }

    @Override
    public List<WaterHeater> getAllWaterHeater() {
        return redisMapper.getDevices(DEVICE_WATER_HEATER, WaterHeater.class);
    }
    @Override
    public WaterHeater getWaterHeaterByName(String name) {
        return waterHeaterMapper.getWaterHeaterByName(name);
    }
}
