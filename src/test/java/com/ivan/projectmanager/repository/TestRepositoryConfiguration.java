package com.ivan.projectmanager.repository;


import com.ivan.projectmanager.config.EntityManagerConfig;
import com.ivan.projectmanager.config.LiquibaseConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration(proxyBeanMethods = false)
@EnableTransactionManagement(proxyTargetClass = true)
@ComponentScan(basePackageClasses = CrudRepository.class)
@Import({LiquibaseConfig.class, EntityManagerConfig.class})
public class TestRepositoryConfiguration {
    @Bean
    public DataSource h2DataSource() {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
    }
}
