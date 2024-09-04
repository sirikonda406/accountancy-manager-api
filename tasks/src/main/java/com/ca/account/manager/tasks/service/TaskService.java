package com.ca.account.manager.tasks.service;


import com.ca.account.manager.common.domain.EmployeeTask;
import com.ca.account.manager.common.domain.IndexDatabase;
import com.ca.account.manager.common.repos.IndexDatabaseRepository;
import com.ca.account.manager.common.repos.TaskRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TaskService {

    private final TaskRepository taskRepository;

    private final IndexDatabaseRepository indexDatabaseRepository;

    public TaskService(TaskRepository taskRepository, IndexDatabaseRepository indexDatabaseRepository) {
        this.taskRepository = taskRepository;
        this.indexDatabaseRepository = indexDatabaseRepository;
    }

    public List<IndexDatabase> rtrvAllTasks(){

        return indexDatabaseRepository.findAll();
 }

    public Optional<EmployeeTask> rtrvTask(Long taskId){

        return taskRepository.findById(taskId);
    }

    public  void createTask(EmployeeTask employeeTask){
        taskRepository.save(employeeTask);
    }
}
