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
import java.util.logging.Logger;


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
    protected static final PostgreSQLContainer<?> POSTGRES;
    private static final Logger LOGGER = Logger.getLogger(DatabaseContainerConfiguration.class.getName());
    private static final String TENANT_DB_SCRIPTS = "db/tenant";
    private static final String TENANT_DATABASE = "pulsewsc7d004881d924624903afe81641a5d8d";
    private static final String TENANT_SCHEMA = "67051f9afc2fe811f805d30f";
    private static final String POSTGRES_DRIVER = "org.postgresql.Driver";
    private static final String BASE_FLYWAY_LOCATION = "classpath:db/migration";


    static {
        POSTGRES = new PostgreSQLContainer<>("postgres:16.4").waitingFor(Wait.defaultWaitStrategy());
        POSTGRES.start();
    }


    protected static void setupTenantDatabases() {
        try (Connection connection = DriverManager.getConnection(
                POSTGRES.getJdbcUrl(), POSTGRES.getUsername(), POSTGRES.getPassword());
             Statement statement = connection.createStatement()
        ) {
            // Create tenant database.
            String createDbSQL = generateCreateDatabaseSQL(TENANT_DATABASE);
            statement.executeUpdate(createDbSQL);

            LOGGER.info("Tenant database created at: " + POSTGRES.getJdbcUrl());

            // Configure Flyway for the tenant database.
            String newJdbcUrl = StringUtils.replace(POSTGRES.getJdbcUrl(), "test", TENANT_DATABASE);
            configureFlyway(newJdbcUrl, POSTGRES.getUsername(), POSTGRES.getPassword()).migrate();

        } catch (SQLException e) {
            LOGGER.severe("Error setting up tenant databases: " + e.getMessage());
        }
    }


    private static String generateCreateDatabaseSQL(String databaseName) {
        return String.format("CREATE DATABASE %s", String.format("\"%s\"", databaseName));
    }

    private static Flyway configureFlyway(String url, String username, String password) {
        return Flyway.configure()
                .locations(TENANT_DB_SCRIPTS)
                .schemas(TENANT_SCHEMA)
                .dataSource(DataSourceBuilder.create()
                        .url(url)
                        .username(username)
                        .password(password)
                        .build())
                .load();
    }


    public static class DataSourceInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(@NotNull ConfigurableApplicationContext applicationContext) {
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                    applicationContext,
                    "spring.test.database.replace=none",
                    "spring.datasource.url=" + POSTGRES.getJdbcUrl(),
                    "spring.datasource.username=" + POSTGRES.getUsername(),
                    "spring.datasource.password=" + POSTGRES.getPassword(),
                    "spring.datasource.driverClassName=" + POSTGRES_DRIVER,
                    "flyway.locations=" + BASE_FLYWAY_LOCATION,
                    "spring.jpa.hibernate.ddl-auto=create",
                    "spring.jpa.show-sql=true",
                    "spring.jpa.properties.hibernate.format_sql=true"
            );

            LOGGER.info("ApplicationContext initialized with URL: " + POSTGRES.getJdbcUrl());
            setupTenantDatabases();
        }
    }

}
