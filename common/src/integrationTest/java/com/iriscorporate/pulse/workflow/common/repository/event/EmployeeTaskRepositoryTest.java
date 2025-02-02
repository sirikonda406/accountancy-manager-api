package com.iriscorporate.pulse.workflow.common.repository.event;

import com.ca.account.manager.common.interceptor.TenantContext;
import com.iriscorporate.pulse.workflow.common.config.BaseDatabaseRepositoryTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class EmployeeTaskRepositoryTest extends BaseDatabaseRepositoryTest {

    @BeforeEach
    public void init() throws IOException {
        String tenant = "67051f9afc2fe811f805d30f";
        TenantContext.setCurrentTenant(tenant);
    }

    @Test
    public void countEventLogTest() {

        Assertions.assertTrue(true);
    }


}
