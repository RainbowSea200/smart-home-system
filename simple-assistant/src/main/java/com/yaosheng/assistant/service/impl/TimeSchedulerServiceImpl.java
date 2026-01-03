package com.yaosheng.assistant.service.impl;

import com.yaosheng.assistant.mapper.TimeSchedulerMapper;
import com.yaosheng.assistant.pojo.TimeScheduler;
import com.yaosheng.assistant.service.TimeSchedulerService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimeSchedulerServiceImpl implements TimeSchedulerService {
    @Resource
    private TimeSchedulerRunnableService timeSchedulerRunnableService;
    @Resource
    private TimeSchedulerMapper timeSchedulerMapper;
    @Override
    public void addTask(TimeScheduler scheduler) {
        timeSchedulerMapper.addTimeScheduler(scheduler);
        timeSchedulerRunnableService.addTask(scheduler);
    }

    @Override
    public void deleteTask(Integer taskId) {
        timeSchedulerMapper.deleteTimeScheduler(taskId);
        timeSchedulerRunnableService.deleteTask(taskId);

    }

    @Override
    public void updateTask(TimeScheduler scheduler) {
        timeSchedulerRunnableService.deleteTask(scheduler.getId());
        timeSchedulerMapper.updateTimeScheduler(scheduler);
        timeSchedulerRunnableService.addTask(scheduler);
    }

    @Override
    public List<TimeScheduler> queryAllTasks() {
        return timeSchedulerMapper.getAllTimeSchedulers();
    }

    @Override
    public List<TimeScheduler> queryTasksByType(Integer type) {
        return timeSchedulerMapper.getTimeScheduler(type);
    }
}
