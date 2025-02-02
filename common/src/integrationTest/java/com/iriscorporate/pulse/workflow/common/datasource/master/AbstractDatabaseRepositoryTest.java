package com.iriscorporate.pulse.workflow.common.datasource.master;

import com.iriscorporate.pulse.workflow.common.config.BaseDatabaseRepositoryTest;

public class AbstractDatabaseRepositoryTest extends BaseDatabaseRepositoryTest {

/*
    @Test
    public void loadIndexDatabases() {
        IndexDatabase indexDatabase = new IndexDatabase();
        indexDatabase.setIdurl(postgres.getJdbcUrl());
        indexDatabase.setIdusername(postgres.getUsername());
        indexDatabase.setIdpassword(postgres.getPassword());
        indexDatabase.setIdschema("tenant1");
        indexDatabase.setIddialect(postgres.getDatabaseName());
        indexDatabase.setIdCode("tenant1");
        indexDatabase.setIdState("active");
        indexDatabase.setIddriver(postgres.getDriverClassName());
        indexDatabase.setIdconfiguration("tmp");

        indexDatabaseRepository.save(indexDatabase);

        assertThat(indexDatabaseRepository.findAll()).doesNotContainNull();

    }*/
}
