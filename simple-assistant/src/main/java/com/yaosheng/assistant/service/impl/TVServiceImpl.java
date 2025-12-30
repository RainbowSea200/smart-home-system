package com.yaosheng.assistant.service.impl;

import static com.yaosheng.assistant.constant.RedisConstant.*;

import com.yaosheng.assistant.constant.RedisConstant;
import com.yaosheng.assistant.mapper.TVMapper;
import com.yaosheng.assistant.mapper.redis.RedisMapper;
import com.yaosheng.assistant.pojo.TV;
import com.yaosheng.assistant.service.TVService;
import com.yaosheng.assistant.util.LocalFileStorageUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TVServiceImpl implements TVService {
    @Resource
    private RedisMapper redisMapper;
    @Resource
    private TVMapper tvMapper;
    @Resource
    private LocalFileStorageUtil util;

    @Override
    @Transactional
    public void addTV(String name, String image) {
        TV tv = new TV(name, image);
        tvMapper.addTV(tv);
        redisMapper.addDevice(DEVICE_TV, tv.getId(), tv);
    }

    @Override
    @Transactional
    public void updateTV(TV tv) {
        tvMapper.updateTV(tv);
        redisMapper.updateDevice(DEVICE_TV, tv.getId(), tv);
    }

    @Override
    @Transactional
    public void deleteTVById(int id) {
        util.deleteFile(getTVById(id).getImage());
        tvMapper.deleteTVById(id);
        redisMapper.deleteDevice(DEVICE_TV, id);
    }

    @Override
    public TV getTVById(int id) {
        return redisMapper.getDevice(DEVICE_TV, id, TV.class);
    }

    @Override
    public List<TV> getAllTV() {
        return redisMapper.getDevices(DEVICE_TV, TV.class);
    }
}
