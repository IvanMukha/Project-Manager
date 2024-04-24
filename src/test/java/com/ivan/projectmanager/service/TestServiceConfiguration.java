package com.ivan.projectmanager.service;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)

@ComponentScan(basePackages = {"com.ivan.projectmanager.config", "com.ivan.projectmanager.service", "com.ivan.projectmanager.repository"})
public class TestServiceConfiguration {
//    @Primary
//    @Bean
//    public DataSource dataSource() {
//        PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
//                .withDatabaseName("test")
//                .withUsername("test")
//                .withPassword("test");
//        postgres.start();
//        PGSimpleDataSource dataSource = new PGSimpleDataSource();
//        dataSource.setUrl(postgres.getJdbcUrl());
//        dataSource.setUser(postgres.getUsername());
//        dataSource.setPassword(postgres.getPassword());
//        return dataSource;
//    }
}