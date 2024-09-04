package com.ca.account.manager.tasks.api;

import com.ca.account.manager.common.domain.EmployeeTask;
import com.ca.account.manager.common.domain.IndexDatabase;
import com.ca.account.manager.tasks.dto.EmployeeTaskDto;
import com.ca.account.manager.tasks.service.TaskService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final ModelMapper modelMapper;

    public TaskController(TaskService taskService, ModelMapper modelMapper) {
        this.taskService = taskService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("first")
    public String rtrvMe() {

        return "first";
    }

    @GetMapping
    public List<IndexDatabase> rtrvTaskList() {

        return taskService.rtrvAllTasks();
    }

    @GetMapping("{taskId}")
    public EmployeeTaskDto rtrvTask(@PathVariable Long taskId) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return  modelMapper.map(taskService.rtrvTask(taskId).get(), EmployeeTaskDto.class);
    }


    @PostMapping("create")
    public void createTask(@RequestBody EmployeeTaskDto employeeTaskDto) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        taskService.createTask(modelMapper.map(employeeTaskDto, EmployeeTask.class));
    }


}
