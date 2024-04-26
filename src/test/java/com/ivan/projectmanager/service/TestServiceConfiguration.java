package com.ivan.projectmanager.service;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@ComponentScan(basePackages = {"com.ivan.projectmanager.config", "com.ivan.projectmanager.service", "com.ivan.projectmanager.repository"})
public class TestServiceConfiguration {
}