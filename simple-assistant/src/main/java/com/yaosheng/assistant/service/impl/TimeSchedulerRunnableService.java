package com.yaosheng.assistant.service.impl;

import com.yaosheng.assistant.mapper.TimeSchedulerMapper;
import com.yaosheng.assistant.pojo.TimeScheduler;
import com.yaosheng.assistant.service.TaskService;
import jakarta.annotation.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Service
public class TimeSchedulerRunnableService {
    private final Map<Integer, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();

    @Resource
    private TaskScheduler taskScheduler;
    @Resource
    private TaskService taskService;

    public void addTask(TimeScheduler scheduler) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime executeTime = LocalDateTime.parse(scheduler.getTime(), formatter);

            ScheduledFuture<?> future = taskScheduler.schedule(
                    () -> taskService.addTask(scheduler),
                    date -> Instant.ofEpochMilli(executeTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
            );

            scheduledTasks.put(scheduler.getId(), future);
        } catch (Exception e) {
            throw new RuntimeException("添加任务失败", e);
        }
    }

    public void deleteTask(Integer taskId) {
        ScheduledFuture<?> future = scheduledTasks.get(taskId);
        if (future != null) {
            future.cancel(false);
            scheduledTasks.remove(taskId);
        }
    }
}
