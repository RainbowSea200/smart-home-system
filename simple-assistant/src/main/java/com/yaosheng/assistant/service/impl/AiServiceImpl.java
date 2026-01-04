package com.yaosheng.assistant.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yaosheng.assistant.pojo.*;
import com.yaosheng.assistant.service.*;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import static com.yaosheng.assistant.constant.DeviceConstant.*;

@Slf4j
@Service
public class AiServiceImpl implements AiService {
    @Resource
    private ChatClient chatClient;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Resource
    private AirConService airConService;
    @Resource
    private LightService lightService;
    @Resource
    private TVService tvService;
    @Resource
    private WaterHeaterService waterHeaterService;



    @Override
    public Result chat(String userInstruction) {
        String prompt = """
            请从用户的自然语言指令中提取以下信息：
            1. 设备类型（必须是以下之一：0（空调）, 1（灯）, 2（电视）, 3（热水器））
            2. 操作（如：0（关闭）, 1（打开））
            3. 设备名称（如：客厅的空调, 卧室的灯, 卫生间的热水器, 客厅的电视等）
            
            用户指令：%s
            
            请以JSON格式返回，只包含以下字段：
            {
                "deviceType": "",
                "action": "",
                "name": ""
            }
            
            示例：
            输入："帮我打开客厅的空调"
            输出：{"deviceType": "0", "action": "1", "name": "客厅的空调"}
            
            输入："关闭卧室的灯"
            输出：{"deviceType": "1", "action": "0", "name": "卧室的灯"}
            
            输入："打开卫生间的热水器"
            输出：{"deviceType": "3", "action": "1", "name": "卫生间的热水器"}
            """.formatted(userInstruction);

        String response = chatClient.prompt(prompt).call().content();
        try {
            InstructionExtractResult result = objectMapper.readValue(response, InstructionExtractResult.class);
            log.info("解析后的结果: {}", result);
            return executeInstruction(result);
        } catch (Exception e) {
            log.error("解析响应失败: {}", e.getMessage());
            return new Result(false, "解析响应失败");
        }
    }
    public Result executeInstruction(InstructionExtractResult result) {
        // 根据解析后的结果执行指令
        if (result.getDeviceType() == null || result.getAction() == null || result.getName() == null) {
            return new Result(false, "指令不完整");
        }
        if (result.getDeviceType().equals(DEVICE_AIR_CON)) {
            // 空调
            AirConditioner device = airConService.getAirConByName(result.getName());
            if (result.getAction().equals(STATUE_ON)) {
                device.setStatue(STATUE_ON);
                airConService.updateAirCon(device);
                // 打开空调
                log.info("打开空调: {}", result.getName());
            } else {
                device.setStatue(STATUE_OFF);
                airConService.updateAirCon(device);
                // 关闭空调
                log.info("关闭空调: {}", result.getName());
            }
        } else if (result.getDeviceType().equals(DEVICE_LIGHT)) {
            // 灯
            Light device = lightService.getLightByName(result.getName());
            if (result.getAction().equals(STATUE_ON)) {
                device.setStatue(STATUE_ON);
                lightService.updateLight(device);
                // 打开灯
                log.info("打开灯: {}", result.getName());
            } else {
                device.setStatue(STATUE_OFF);
                lightService.updateLight(device);
                // 关闭灯
                log.info("关闭灯: {}", result.getName());
            }
        } else if (result.getDeviceType().equals(DEVICE_TV)) {
            // 电视
            TV device = tvService.getTVByName(result.getName());
            if (result.getAction().equals(STATUE_ON)) {
                device.setStatue(STATUE_ON);
                tvService.updateTV(device);
                // 打开电视
                log.info("打开电视: {}", result.getName());
            } else {
                device.setStatue(STATUE_OFF);
                tvService.updateTV(device);
                // 关闭电视
                log.info("关闭电视: {}", result.getName());
            }
        } else if (result.getDeviceType().equals(DEVICE_WATER_HEATER)) {
            // 热水器
            WaterHeater device = waterHeaterService.getWaterHeaterByName(result.getName());
            if (result.getAction().equals(STATUE_ON)) {
                device.setStatue(STATUE_ON);
                waterHeaterService.updateWaterHeater(device);
                // 打开热水器
                log.info("打开热水器: {}", result.getName());
            } else {
                device.setStatue(STATUE_OFF);
                waterHeaterService.updateWaterHeater(device);
                // 关闭热水器
                log.info("关闭热水器: {}", result.getName());
            }
        } else {
            return new Result(false, "不支持的设备类型");
        }

        // 这里可以添加实际的执行逻辑，如调用设备控制接口
        return new Result(true, "指令执行成功");
    }
}
