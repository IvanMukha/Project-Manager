package com.ivan.projectmanager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ivan.projectmanager.config.ApplicationConfig;
import com.ivan.projectmanager.controller.TaskController;
import com.ivan.projectmanager.dto.ProjectDTO;
import com.ivan.projectmanager.dto.TaskDTO;
import com.ivan.projectmanager.dto.TeamDTO;
import com.ivan.projectmanager.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        UserDTO userDTO = new UserDTO();
        UserDTO userDTO1 = new UserDTO();
        userDTO.setUsername("username1").setPassword("password1").setEmail("email1");
        userDTO1.setUsername("username2").setPassword("password2").setEmail("email2");

        TeamDTO teamDTO = new TeamDTO();
        TeamDTO teamDTO1 = new TeamDTO();
        teamDTO.setName("name1");
        teamDTO1.setName("name2");

        ProjectDTO projectDTO = new ProjectDTO();
        ProjectDTO projectDTO1 = new ProjectDTO();
        projectDTO.setDescription("description1").setStatus("status1").setTitle("title1").setStartDate(LocalDateTime.now()).setManagerId(1L).setTeamId(1L);
        projectDTO1.setDescription("description2").setStatus("status2").setTitle("title2").setStartDate(LocalDateTime.now()).setManagerId(2L).setTeamId(2L);

        TaskDTO taskDTO = new TaskDTO();
        TaskDTO taskDTO1 = new TaskDTO();
        taskDTO.setDescription("description1").setStatus("status1").setTitle("title1").
                setStartDate(LocalDateTime.now()).setAssignee(1L).setProjectId(1L).setCategory("category1").
                setDueDate(LocalDateTime.now()).setLabel("label1").setPriority("priority1").setReporter(1L);

        taskDTO1.setDescription("description2").setStatus("status2").setTitle("title2").
                setStartDate(LocalDateTime.now()).setAssignee(2L).setProjectId(2L).setCategory("category2").
                setDueDate(LocalDateTime.now()).setLabel("label2").setPriority("priority2").setReporter(2L);

        TaskController taskController = context.getBean(TaskController.class);

        Runnable saveTaskWithRelatedEntities1 = () -> {

            try {
                log.info("saveTaskWithRelatedEntities1: {}", taskController.createTaskWithRelatedEntities(taskDTO, userDTO, teamDTO, projectDTO));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        };
        Runnable saveTaskWithRelatedEntities2 = () -> {

            try {
                log.info("saveTaskWithRelatedEntities2: {}", taskController.createTaskWithRelatedEntities(taskDTO1, userDTO1, teamDTO1, projectDTO1));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        };

        Runnable getAllTasks = () -> {
            try {
                log.info("Tasks: {}", taskController.getAll());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        };

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Future<?> future1 = executorService.submit(saveTaskWithRelatedEntities1);
        Future<?> future2 = executorService.submit(saveTaskWithRelatedEntities2);
        Future<?> future3 = executorService.submit(getAllTasks);
        try {
            future1.get();
            future2.get();
            future3.get();
        } catch (ExecutionException e) {
            Throwable rootCause = e.getCause();
            if (rootCause != null) {
                log.info("rootCause not null", e);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        executorService.shutdown();
        while (!executorService.isTerminated()) {
        }
        context.close();
    }
}