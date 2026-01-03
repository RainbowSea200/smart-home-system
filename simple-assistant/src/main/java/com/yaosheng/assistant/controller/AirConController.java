package com.yaosheng.assistant.controller;

import com.yaosheng.assistant.pojo.AirConditioner;
import com.yaosheng.assistant.pojo.TimeScheduler;
import com.yaosheng.assistant.service.AirConService;
import com.yaosheng.assistant.service.TimeSchedulerService;
import com.yaosheng.assistant.util.LocalFileStorageUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/aircon")
public class AirConController {
    @Resource
    private AirConService airConService;
    @Resource
    private LocalFileStorageUtil util;
    @Resource
    private TimeSchedulerService timeSchedulerService;

    @GetMapping("/get/{id}")
    public AirConditioner getAirConById(@PathVariable Integer id) {
        return airConService.getAirConById(id);
    }

    @GetMapping("/getAll")
    public List<AirConditioner> getAllAirCon() {
        List<AirConditioner> allAirCon = airConService.getAllAirCon();
        log.info("空调：{}", allAirCon);
        return allAirCon;
    }

    @PostMapping("/add")
    public void addAirCon(@RequestParam("name") String name, @RequestParam("image") MultipartFile image) {
        try {
            airConService.addAirCon(name, util.saveImage(image));
        } catch (IOException e) {
            log.error("保存图片失败: {}", e.getMessage());
        }
    }

    @PostMapping("/update")
    public void updateAirCon(@RequestBody AirConditioner airConditioner) {
        airConService.updateAirCon(airConditioner);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAirConById(@PathVariable Integer id) {
        airConService.deleteAirConById(id);
    }

    @PostMapping("/addTask")
    public void addTask(@RequestBody TimeScheduler scheduler) {
        timeSchedulerService.addTask(scheduler);
    }
}
