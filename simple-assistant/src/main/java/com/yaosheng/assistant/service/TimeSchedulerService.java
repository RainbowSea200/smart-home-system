package com.yaosheng.assistant.service;

import com.yaosheng.assistant.pojo.TimeScheduler;

import java.util.List;

public interface TimeSchedulerService {
    // 添加任务
    void addTask(TimeScheduler scheduler);

    // 删除任务
    void deleteTask(Integer taskId);

    // 修改任务
    void updateTask(TimeScheduler scheduler);

    // 查询所有任务
    List<TimeScheduler> queryAllTasks();

    // 根据设备类型查询任务
    List<TimeScheduler> queryTasksByType(Integer type);
}
