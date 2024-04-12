package com.ivan.projectManager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ivan.projectManager.config.ApplicationConfig;
import com.ivan.projectManager.controller.TaskController;
import com.ivan.projectManager.dto.ProjectDTO;
import com.ivan.projectManager.dto.TaskDTO;
import com.ivan.projectManager.dto.TeamDTO;
import com.ivan.projectManager.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        UserDTO userDTO = new UserDTO();
        UserDTO userDTO1 = new UserDTO();
        userDTO.setId(1).setUsername("username1").setPassword("password1").setEmail("email1");
        userDTO1.setId(2).setUsername("username2").setPassword("password2").setEmail("email2");

        TeamDTO teamDTO = new TeamDTO();
        TeamDTO teamDTO1 = new TeamDTO();
        teamDTO.setId(1).setName("name1");
        teamDTO1.setId(2).setName("name2");

        ProjectDTO projectDTO = new ProjectDTO();
        ProjectDTO projectDTO1 = new ProjectDTO();
        projectDTO.setId(1).setDescription("description1").setStatus("status1").setTitle("title1").setStartDate(LocalDateTime.now()).setManagerId(1).setTeamId(1);
        projectDTO1.setId(2).setDescription("description2").setStatus("status2").setTitle("title2").setStartDate(LocalDateTime.now()).setManagerId(2).setTeamId(2);

        TaskDTO taskDTO = new TaskDTO();
        TaskDTO taskDTO1 = new TaskDTO();
        taskDTO.setId(1).setDescription("description1").setStatus("status1").setTitle("title1").
                setStartDate(LocalDateTime.now()).setAssignee(1).setProjectId(1).setCategory("category1").
                setDueDate(LocalDateTime.now()).setLabel("label1").setPriority("priority1").setReporter(1);

        taskDTO1.setId(2).setDescription("description2").setStatus("status2").setTitle("title2").
                setStartDate(LocalDateTime.now()).setAssignee(2).setProjectId(2).setCategory("category2").
                setDueDate(LocalDateTime.now()).setLabel("label2").setPriority("priority2").setReporter(2);

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
        executorService.submit(saveTaskWithRelatedEntities1);
        executorService.submit(saveTaskWithRelatedEntities2);
        executorService.submit(getAllTasks);

        executorService.shutdown();
        while (!executorService.isTerminated()) {
        }
        context.close();
    }
}