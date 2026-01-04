package com.yaosheng.assistant.service.impl;

import com.yaosheng.assistant.constant.RedisConstant;
import com.yaosheng.assistant.mapper.LightMapper;
import com.yaosheng.assistant.mapper.redis.RedisMapper;
import com.yaosheng.assistant.pojo.Light;
import com.yaosheng.assistant.service.LightService;
import com.yaosheng.assistant.util.LocalFileStorageUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LightServiceImpl implements LightService {
    @Resource
    private RedisMapper redisMapper;
    @Resource
    private LightMapper lightMapper;
    @Resource
    private LocalFileStorageUtil util;

    @Override
    @Transactional
    public void addLight(String name, String image) {
        Light light = new Light(name, image);
        lightMapper.addLight(light);
        redisMapper.addDevice(RedisConstant.DEVICE_LIGHT, light.getId(), light);
    }

    @Override
    @Transactional
    public void updateLight(Light light) {
        lightMapper.updateLight(light);
        redisMapper.updateDevice(RedisConstant.DEVICE_LIGHT, light.getId(), light);
    }

    @Override
    @Transactional
    public void deleteLightById(int id) {
        util.deleteFile(getLightById(id).getImage());
        lightMapper.deleteLightById(id);
        redisMapper.deleteDevice(RedisConstant.DEVICE_LIGHT, id);
    }

    @Override
    public Light getLightById(int id) {
        return redisMapper.getDevice(RedisConstant.DEVICE_LIGHT, id, Light.class);
    }

    @Override
    public List<Light> getAllLight() {
        return redisMapper.getDevices(RedisConstant.DEVICE_LIGHT, Light.class);
    }
    @Override
    public Light getLightByName(String name) {
        return lightMapper.getLightByName(name);
    }
}
