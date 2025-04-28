package com.ca.account.manager.common;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@ComponentScan
@Configuration
public class CommonConfig {

    private DataSourceProperties dataSourceProperties;

    public CommonConfig(DataSourceProperties dataSourceProperties) {
        this.dataSourceProperties = dataSourceProperties;
    }

    @Bean(name = "indexDataSource")
    public DataSource indexDataSource() {
        return DataSourceBuilder.create().driverClassName(dataSourceProperties.getDriverClassName()).url(dataSourceProperties.getUrl())
                .username(dataSourceProperties.getUsername()).password(dataSourceProperties.getPassword()).type(HikariDataSource.class).build();
    }

}
