package com.yaosheng.assistant.mapper.redis;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.yaosheng.assistant.mapper.AirConMapper;
import com.yaosheng.assistant.mapper.LightMapper;
import com.yaosheng.assistant.mapper.TVMapper;
import com.yaosheng.assistant.mapper.WaterHeaterMapper;
import com.yaosheng.assistant.pojo.AirConditioner;
import com.yaosheng.assistant.pojo.Light;
import com.yaosheng.assistant.pojo.TV;
import com.yaosheng.assistant.pojo.WaterHeater;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class RedisMapper {
    @Resource
    private StringRedisTemplate redis;
    @Resource
    private RedissonClient redissonClient;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private AirConMapper airConMapper;
    @Resource
    private LightMapper lightMapper;
    @Resource
    private TVMapper tvMapper;
    @Resource
    private WaterHeaterMapper waterHeaterMapper;


    public <T> void addDevice(String preKey, Integer deviceId, T device) {
        try {
            redis.opsForValue().set(preKey + ":" + deviceId, JSONUtil.toJsonStr(device));
            redis.opsForList().leftPush(preKey, deviceId.toString());
        } catch (Exception e) {
            log.error("保存设备到Redis失败: type={}, id={}", preKey, deviceId, e);
            throw new RuntimeException("保存设备到Redis失败", e);
        }
    }

    public <T> T getDevice(String preKey, Integer deviceId, Class<T> clazz) {
        try {
            String device = redis.opsForValue().get(preKey + ":" + deviceId);
            log.info("从Redis获取设备: type={}, id={}, device={}", preKey, deviceId, device);
            if (StrUtil.isBlank(device)) {
                T newDevice = getDeviceUseSQL(clazz, deviceId);
                if (newDevice == null) {
                    log.info("无设备 -> type={},id={}", clazz, deviceId);
                    return null;
                }
                log.info("从数据库获取设备: type={}, id={}, device={}", preKey, deviceId, newDevice);
                addDevice(preKey, deviceId, newDevice);
                return newDevice;
            } else {
                return JSONUtil.toBean(device, clazz);
            }
        } catch (Exception e) {
            log.error("从Redis获取设备失败: type={}, id={}", preKey, deviceId, e);
            throw new RuntimeException("从Redis获取设备失败", e);
        }
    }

    public <T> void updateDevice(String preKey, Integer deviceId, T device) {
        try {
            redis.opsForValue().set(preKey + ":" + deviceId, JSONUtil.toJsonStr(device));
        } catch (Exception e) {
            log.error("更新设备到Redis失败: type={}, id={}", preKey, deviceId, e);
            throw new RuntimeException("更新设备到Redis失败", e);
        }
    }

    public List<Integer> getDeviceIds(String key) {
        List<String> ids = redis.opsForList().range(key, 0, -1);
        return ids.stream().map(Integer::parseInt).toList();
    }

    public <T> List<T> getDevices(String key, Class<T> clazz) {
        List<Integer> ids = getDeviceIds(key);
        log.info("获取设备列表:  ids={}", ids);
        return ids.stream().map(id -> getDevice(key, id, clazz)).toList();
    }

    public void deleteDevice(String preKey, Integer deviceId) {
        try {
            redis.opsForList().remove(preKey, 1, deviceId.toString());
            redis.delete(preKey + ":" + deviceId);
        } catch (Exception e) {
            log.error("从Redis删除设备失败: type={}, id={}", preKey, deviceId, e);
            throw new RuntimeException("从Redis删除设备失败", e);
        }
    }


    public <T> T getDeviceUseSQL(Class<T> clazz, Integer deviceId) {
        if (clazz.equals(AirConditioner.class)) {
            return clazz.cast(airConMapper.getAirConById(deviceId));
        } else if (clazz.equals(Light.class)) {
            return clazz.cast(lightMapper.getLightById(deviceId));
        } else if (clazz.equals(TV.class)) {
            return clazz.cast(tvMapper.getTV(deviceId));
        } else if (clazz.equals(WaterHeater.class)) {
            return clazz.cast(waterHeaterMapper.getWaterHeaterById(deviceId));
        } else {
            throw new RuntimeException("不支持的设备类型");
        }
    }
}
