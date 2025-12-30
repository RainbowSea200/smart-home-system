package com.yaosheng.assistant.controller;

import com.yaosheng.assistant.pojo.WaterHeater;
import com.yaosheng.assistant.service.WaterHeaterService;
import com.yaosheng.assistant.util.LocalFileStorageUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/waterheater")
public class WaterHeaterController {
    @Resource
    private WaterHeaterService waterHeaterService;
    @Resource
    private LocalFileStorageUtil util;

    @GetMapping("/get/{id}")
    public WaterHeater getWaterHeaterById(@PathVariable Integer id) {
        return waterHeaterService.getWaterHeaterById(id);
    }
    @GetMapping("/getAll")
    public List<WaterHeater> getAllWaterHeater() {
        return waterHeaterService.getAllWaterHeater();
    }
    @PostMapping("/add")
    public void addWaterHeater(@RequestParam("name") String name, @RequestParam("image") MultipartFile image) {
        try {
            waterHeaterService.addWaterHeater(name, util.saveImage(image));
        }catch (IOException e){
            log.error("保存图片失败: {}", e.getMessage());
        }
    }
    @PostMapping("/update")
    public void updateWaterHeater(@RequestBody WaterHeater waterHeater) {
        waterHeaterService.updateWaterHeater(waterHeater);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteWaterHeaterById(@PathVariable Integer id) {
        waterHeaterService.deleteWaterHeaterById(id);
    }
}
