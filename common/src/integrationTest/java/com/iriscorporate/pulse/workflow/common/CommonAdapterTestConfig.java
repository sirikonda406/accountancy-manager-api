package com.iriscorporate.pulse.workflow.common;

import com.ca.account.manager.common.CommonConfig;
import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({CommonConfig.class})
public class CommonAdapterTestConfig {


    @Bean
    public FlywayMigrationStrategy flywayMigrationStrategy() {

        return (flywayOld) -> {
            Flyway.configure()
                    .locations("db/migration/index")
                    .configuration(flywayOld.getConfiguration())
                    .load().migrate();

        };
    }

}
