package com.yaosheng.assistant.controller;

import com.yaosheng.assistant.util.SseNotifyUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
@RequestMapping("/sse")
public class SseNotifyController {

    // 标记定时任务是否执行完成（原子类保证线程安全）
    private final AtomicBoolean isTaskCompleted = new AtomicBoolean(false);

    // SSE 接口：前端连接地址为 http://localhost:8080/sse/task-notify
    @GetMapping("/task-notify")
    public void sseTaskNotify(HttpServletResponse response) throws Exception {
        // 1. 设置 SSE 响应头（核心，必须配置）
        response.setContentType("text/event-stream"); // 声明响应格式为 SSE
        response.setCharacterEncoding("UTF-8");
        response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache"); // 禁用缓存
        response.setHeader(HttpHeaders.CONNECTION, "keep-alive"); // 保持长连接

        // 2. 获取 PrintWriter，用于向前端推送消息
        PrintWriter printWriter = response.getWriter();

        // 3. 保持连接，监听任务执行状态（模拟持续推送，可根据需求调整）
        while (true) {
            if (isTaskCompleted.get()) {
                // 步骤4：任务执行完成，向前端推送 SSE 通知
                String sseMessage = SseNotifyUtil.buildSseMessage("TASK_COMPLETE", "SSE：定时任务执行完成，数据已更新");
                printWriter.write(sseMessage);
                printWriter.flush(); // 刷新缓冲区，立即发送消息

                // 可选：任务通知后，重置任务状态，关闭连接（或保持连接继续监听）
                isTaskCompleted.set(false);
                // break; // 关闭连接（如需持续推送，注释该行）
            }

            // 模拟间隔，避免CPU占用过高
            Thread.sleep(1000);
        }
    }

    // 供定时任务调用：标记任务执行完成
    public void markTaskCompleted() {
        isTaskCompleted.set(true);
    }
}