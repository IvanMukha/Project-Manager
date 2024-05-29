package com.ivan.projectmanager.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:application.properties")
public class LiquibaseConfig {
    @Bean
    public SpringLiquibase liquibase(DataSource dataSource,
                                     @Value("${liquibase.changelog-master}") String liquibaseChangeLog) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog(liquibaseChangeLog);
        return liquibase;
    }
}
