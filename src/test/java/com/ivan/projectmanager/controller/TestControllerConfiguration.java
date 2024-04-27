package com.ivan.projectmanager.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;

@Configuration(proxyBeanMethods = false)
@ComponentScan(basePackages = {"com.ivan.projectmanager.config", "com.ivan.projectmanager.service", "com.ivan.projectmanager.controller"})
public class TestControllerConfiguration {
    @Bean(initMethod = "start", destroyMethod = "stop")
    public PostgreSQLContainer<?> postgreSQLContainer() {
        return new PostgreSQLContainer<>("postgres:16.0");
    }

    @Primary
    @Bean
    public DataSource dataSource1(PostgreSQLContainer<?> postgreSQLContainer) {
        return new DriverManagerDataSource(
                postgreSQLContainer.getJdbcUrl(),
                postgreSQLContainer.getUsername(),
                postgreSQLContainer.getPassword()
        );
    }
}
