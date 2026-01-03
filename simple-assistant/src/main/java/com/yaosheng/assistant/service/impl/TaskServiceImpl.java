package com.yaosheng.assistant.service.impl;

import cn.hutool.json.JSONUtil;
import com.yaosheng.assistant.controller.SseNotifyController;
import com.yaosheng.assistant.pojo.*;
import com.yaosheng.assistant.service.*;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import static com.yaosheng.assistant.constant.DeviceConstant.*;

@Slf4j
@Service
public class TaskServiceImpl implements TaskService {
    @Resource
    private SseNotifyController sseNotifyController;
    @Resource
    @Lazy
    private TimeSchedulerService timeSchedulerService;
    @Resource
    private AirConService airConService;
    @Resource
    private LightService lightService;
    @Resource
    private TVService tvService;
    @Resource
    private WaterHeaterService waterHeaterService;
    @Override
    public void addTask(TimeScheduler timeScheduler) {
        if(DEVICE_AIR_CON.equals(timeScheduler.getType())){
            airConService.updateAirCon(JSONUtil.toBean(timeScheduler.getDevice(), AirConditioner.class));
        }else if(DEVICE_LIGHT.equals(timeScheduler.getType())){
            lightService.updateLight(JSONUtil.toBean(timeScheduler.getDevice(), Light.class));
        }else if(DEVICE_TV.equals(timeScheduler.getType())){
            tvService.updateTV(JSONUtil.toBean(timeScheduler.getDevice(), TV.class));
        }else if(DEVICE_WATER_HEATER.equals(timeScheduler.getType())){
            waterHeaterService.updateWaterHeater(JSONUtil.toBean(timeScheduler.getDevice(), WaterHeater.class));
        }
        log.info("任务已完成: {}", timeScheduler);
        timeSchedulerService.deleteTask(timeScheduler.getId());
        sseNotifyController.markTaskCompleted();
    }
}
