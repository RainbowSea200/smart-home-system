package com.yaosheng.assistant.controller;

import com.yaosheng.assistant.pojo.TimeScheduler;
import com.yaosheng.assistant.service.TimeSchedulerService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Resource
    private TimeSchedulerService timeSchedulerService;
    @GetMapping("/getAll")
    public List<TimeScheduler> getAllTask() {
        return timeSchedulerService.queryAllTasks();
    }
    @GetMapping("/getByType/{type}")
    public List<TimeScheduler> getTaskByType(@PathVariable Integer type) {
        return timeSchedulerService.queryTasksByType(type);
    }
    @DeleteMapping("/delete/{taskId}")
    public void deleteTask(@PathVariable Integer taskId) {
        timeSchedulerService.deleteTask(taskId);
    }
    @PutMapping("/update")
    public void updateTask(@RequestBody TimeScheduler timeScheduler) {
        timeSchedulerService.updateTask(timeScheduler);
    }
}
