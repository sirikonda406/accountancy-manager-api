package com.iriscorporate.pulse.workflow.common.config;

import com.iriscorporate.pulse.workflow.common.CommonAdapterTestConfig;
import org.apache.commons.lang3.StringUtils;
import org.flywaydb.core.Flyway;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("IndexDatabase repository tests.")
@ActiveProfiles("test")
@Testcontainers
@ContextConfiguration(classes = CommonAdapterTestConfig.class, initializers = DatabaseContainerConfiguration.DataSourceInitializer.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional("indexTransactionManager")
public class DatabaseContainerConfiguration {

    @Container
    protected final static PostgreSQLContainer<?> postgres;

    private final static String TENANT_DB_SCRIPTS = "db/tenant";

    private static final String tenantDatabase = "pulsewsc7d004881d924624903afe81641a5d8d";
    private static final String tenant = "67051f9afc2fe811f805d30f";

    static {
        postgres = new PostgreSQLContainer<>("postgres:16.4").waitingFor((Wait.defaultWaitStrategy()));
        postgres.start();
    }

    protected static void setupTenantDatabases() {
        try (Connection conn = DriverManager.getConnection(postgres.getJdbcUrl(), postgres.getUsername(), postgres.getPassword());
             Statement stmt = conn.createStatement()
        ) {
            String sql = String.format("CREATE DATABASE %s", String.format("\"%s\"", tenantDatabase));
            stmt.executeUpdate(sql);

            System.out.println("createAndPopulateTenantDatabase============================================" + postgres.getJdbcUrl());
            String newURL = StringUtils.replace(postgres.getJdbcUrl(), "test", tenantDatabase);
            Flyway.configure().locations(TENANT_DB_SCRIPTS).schemas(tenant).dataSource(DataSourceBuilder.create().url(newURL).password(postgres.getPassword()).username(postgres.getUsername()).build()).load().migrate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static class DataSourceInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(@NotNull ConfigurableApplicationContext applicationContext) {
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                    applicationContext,
                    "spring.test.database.replace=none",
                    "spring.datasource.url=" + postgres.getJdbcUrl(),
                    "spring.datasource.username=" + postgres.getUsername(),
                    "spring.datasource.password=" + postgres.getPassword(),
                    "spring.datasource.driverClassName=org.postgresql.Driver",
                    "flyway.locations=classpath:db/migration",
                    "spring.jpa.hibernate.ddl-auto=create",
                    "spring.jpa.show-sql=true",
                    "spring.jpa.properties.hibernate.format_sql=true"
            );

            System.out.println("ApplicationContextInitializer============================================" + postgres.getJdbcUrl());
            setupTenantDatabases();
        }
    }
}
