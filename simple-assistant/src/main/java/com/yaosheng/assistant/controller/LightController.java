package com.yaosheng.assistant.controller;

import com.yaosheng.assistant.pojo.Light;
import com.yaosheng.assistant.pojo.TimeScheduler;
import com.yaosheng.assistant.service.LightService;
import com.yaosheng.assistant.service.TimeSchedulerService;
import com.yaosheng.assistant.util.LocalFileStorageUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/light")
public class LightController {
    @Resource
    private LightService lightService;
    @Resource
    private LocalFileStorageUtil util;
    @Resource
    private TimeSchedulerService timeSchedulerService;
    @PostMapping("/add")
    public void addLight(@RequestParam("name") String name, @RequestParam("image") MultipartFile image) {
        try {
            lightService.addLight(name, util.saveImage(image));
        } catch (IOException e) {
            log.error("保存图片失败: {}", e.getMessage());
        }
    }
    @RequestMapping("/get/{id}")
    public Light getLight(@PathVariable Integer id) {
        return lightService.getLightById(id);
    }
    @RequestMapping("/getAll")
    public List<Light> getAllLight() {
        return lightService.getAllLight();
    }
    @PostMapping("/update")
    public void updateLight(@RequestBody Light light) {
        lightService.updateLight(light);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteLight(@PathVariable Integer id) {
        lightService.deleteLightById(id);
    }
    @PostMapping("/addTask")
    public void addTask(@RequestBody TimeScheduler timeScheduler) {
        timeSchedulerService.addTask(timeScheduler);
    }
}
