package com.ca.account.manager.tasks.api;

import com.ca.account.manager.common.repos.IndexDatabaseRepository;
import com.ca.account.manager.tasks.TasksConfig;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;

@Configuration
@Import({
        TasksConfig.class
})
public class TasksAdapterTestConfig {

   @MockBean
   private DataSource indexDataSource;

   @MockBean
   private ModelMapper modelMapper;

   @MockBean
   private IndexDatabaseRepository indexDatabaseRepository;
}
