package com.yaosheng.assistant.controller;

import com.yaosheng.assistant.pojo.TV;
import com.yaosheng.assistant.pojo.TimeScheduler;
import com.yaosheng.assistant.service.TVService;
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
@RequestMapping("/tv")
public class TVController {
    @Resource
    private TVService tvService;
    @Resource
    private LocalFileStorageUtil util;
    @Resource
    private TimeSchedulerService timeSchedulerService;
    @GetMapping("/get/{id}")
    public TV getTVById(@PathVariable Integer id) {
        return tvService.getTVById(id);
    }
    @GetMapping("/getAll")
    public List<TV> getAllTV() {
        return tvService.getAllTV();
    }
    @PostMapping("/add")
    public void addTV(@RequestParam("name") String name, @RequestParam("image") MultipartFile image) {
        try {
            tvService.addTV(name, util.saveImage(image));
        } catch (IOException e) {
            log.error("保存图片失败: {}", e.getMessage());
        }
    }
    @PostMapping("/update")
    public void updateTV(@RequestBody TV tv) {
        tvService.updateTV(tv);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteTVById(@PathVariable("id") Integer id) {
        tvService.deleteTVById(id);
    }
    @PostMapping("/addTask")
    public void addTask(@RequestBody TimeScheduler timeScheduler) {
        timeSchedulerService.addTask(timeScheduler);
    }
}
