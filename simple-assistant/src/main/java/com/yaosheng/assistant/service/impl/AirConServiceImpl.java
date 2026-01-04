package com.yaosheng.assistant.service.impl;

import com.yaosheng.assistant.mapper.AirConMapper;
import com.yaosheng.assistant.mapper.redis.RedisMapper;
import com.yaosheng.assistant.pojo.AirConditioner;
import com.yaosheng.assistant.service.AirConService;
import com.yaosheng.assistant.util.LocalFileStorageUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.yaosheng.assistant.constant.RedisConstant.*;

@Slf4j
@Service
public class AirConServiceImpl implements AirConService {
    @Resource
    private RedisMapper redisMapper;
    @Resource
    private AirConMapper airConMapper;
    @Resource
    private LocalFileStorageUtil util;
    @Override
    @Transactional
    public void addAirCon(String name, String image) {
        AirConditioner airConditioner = new AirConditioner(name, image);
        airConMapper.addAirCon(airConditioner);
        redisMapper.addDevice(DEVICE_AIR_CON,airConditioner.getId(),airConditioner);
    }

    @Override
    @Transactional
    public void updateAirCon(AirConditioner airConditioner) {
        airConMapper.updateAirCon(airConditioner);
        redisMapper.updateDevice(DEVICE_AIR_CON,airConditioner.getId(),airConditioner);
    }

    @Override
    @Transactional
    public void deleteAirConById(int id) {
        AirConditioner airCon = getAirConById(id);
        util.deleteFile(airCon.getImage());
        airConMapper.deleteAirConById(id);
        redisMapper.deleteDevice(DEVICE_AIR_CON,id);
    }

    @Override
    public AirConditioner getAirConById(int id) {
        return redisMapper.getDevice(DEVICE_AIR_CON,id,AirConditioner.class);
    }

    @Override
    public List<AirConditioner> getAllAirCon() {
        return redisMapper.getDevices(DEVICE_AIR_CON, AirConditioner.class);
    }
    @Override
    public AirConditioner getAirConByName(String name) {
        return airConMapper.getAirConByName(name);
    }
}
