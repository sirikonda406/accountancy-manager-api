package com.ca.account.manager.common.repos;

import com.ca.account.manager.common.BaseDatabaseRepositoryTest;
import com.ca.account.manager.common.datasource.master.IndexDatabase;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class IndexDatabaseRepositoryTest extends BaseDatabaseRepositoryTest {

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

    }
}
