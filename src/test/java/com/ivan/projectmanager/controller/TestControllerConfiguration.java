package com.ivan.projectmanager.controller;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;

@Configuration(proxyBeanMethods = false)
@ComponentScan(basePackages = {"com.ivan.projectmanager.config", "com.ivan.projectmanager.service", "com.ivan.projectmanager.controller"})
public class TestControllerConfiguration {
    @Primary
    @Bean
    public DataSource dataSource() {
        PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
                .withDatabaseName("test")
                .withUsername("test")
                .withPassword("test");
        postgres.start();
        System.out.println("container info:"+postgres.getContainerInfo());
        System.out.println(postgres.getMappedPort(PostgreSQLContainer.POSTGRESQL_PORT));
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(postgres.getJdbcUrl());
        dataSource.setUser(postgres.getUsername());
        dataSource.setPassword(postgres.getPassword());
        return dataSource;
    }
}
