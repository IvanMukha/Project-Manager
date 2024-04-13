package com.ivan.projectManager.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ivan.projectManager.Application;
import com.ivan.projectManager.utils.ConnectionHolder;
import liquibase.integration.spring.SpringLiquibase;
import org.modelmapper.ModelMapper;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;


@Configuration
@ComponentScan(basePackageClasses = Application.class)
@PropertySource("classpath:application.properties")
@EnableAspectJAutoProxy
public class ApplicationConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();

    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerModule(new Jdk8Module());
        return objectMapper;
    }

    @Bean
    public DataSource dataSource(@Value("${url}") String url, @Value("${usernameValue}") String username, @Value("${password}") String password) {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(url);
        dataSource.setUser(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    public SpringLiquibase liquibase(DataSource dataSource,
            @Value("${liquibase.changelog-master}") String liquibaseChangeLog) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog(liquibaseChangeLog);
        return liquibase;
    }

    @Bean
    public ConnectionHolder connectionHolder(DataSource dataSource) {
        return new ConnectionHolder(dataSource);
    }


}
