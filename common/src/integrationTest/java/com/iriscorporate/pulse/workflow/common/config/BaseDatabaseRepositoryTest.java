package com.iriscorporate.pulse.workflow.common.config;

import com.ca.account.manager.common.repository.EmployeeTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public abstract class BaseDatabaseRepositoryTest extends DatabaseContainerConfiguration{

    @Autowired
    private EmployeeTaskRepository employeeTaskRepository;

}
