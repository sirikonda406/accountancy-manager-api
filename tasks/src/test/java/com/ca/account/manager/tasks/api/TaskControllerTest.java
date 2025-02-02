package com.ca.account.manager.tasks.api;

import com.ca.account.manager.tasks.TasksAdapterTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class TaskControllerTest extends TasksAdapterTest {

    @Autowired
    private TaskController taskController;

    @Test
    public void rtrvTask() throws Exception {

        assertThat(taskController.rtrvMe()).isEqualTo("first");

    }

}
