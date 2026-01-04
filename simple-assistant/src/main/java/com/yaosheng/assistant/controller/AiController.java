package com.yaosheng.assistant.controller;

import com.yaosheng.assistant.pojo.Result;
import com.yaosheng.assistant.service.AiService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/ai")
public class AiController {
    @Resource
    private AiService aiService;

    @PostMapping(value = "/chat", produces = "text/html;charset=UTF-8")
    public Result chat(@RequestBody String message) {
        log.info("message: {}", message);
        Result chat = aiService.chat(message);
        log.info("chat: {}", chat);
        return chat;
    }
}
