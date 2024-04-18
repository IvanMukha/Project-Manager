package com.ivan.projectmanager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ivan.projectmanager.controller.TaskController;
import com.ivan.projectmanager.dto.ProjectDTO;
import com.ivan.projectmanager.dto.TaskDTO;
import com.ivan.projectmanager.dto.TeamDTO;
import com.ivan.projectmanager.dto.UserDTO;
import com.ivan.projectmanager.model.User;
import com.ivan.projectmanager.repository.impl.UserRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.ivan.projectmanager.config.ApplicationConfig;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
//        UserRepositoryImpl userRepository= context.getBean(UserRepositoryImpl.class);
//        List<User> users=userRepository.getByUsernameJPQL("username1");
//        for(User u:users){
//            log.info("UserJPQL: {} {} {} {}", u.getId(), u.getUsername(), u.getPassword(), u.getEmail());
//        }
//        List<User> users2=userRepository.getByUsernameCriteria("username2");
//        for(User u:users2){
//            log.info("UserCreteria: {} {} {} {}", u.getId(), u.getUsername(), u.getPassword(), u.getEmail());
//        }

    }
}