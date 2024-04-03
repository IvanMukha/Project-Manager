package org.example.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.application.controller.*;
import org.example.application.dto.*;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
@ComponentScan(basePackageClasses = Application.class)
public class Application {

    public static void main(String[] args) throws JsonProcessingException {
        Logger log = LoggerFactory.getLogger(Application.class);
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
        // attachment
        AttachmentController attachmentController = context.getBean(AttachmentController.class);
        AttachmentDTO attachmentDTO = new AttachmentDTO();
        AttachmentDTO attachmentDTO1 = new AttachmentDTO();
        AttachmentDTO attachmentDTO2 = new AttachmentDTO();
        attachmentDTO.setId(1).setTitle("title1").setTaskId(1);
        attachmentDTO1.setId(2).setTitle("title2").setTaskId(2);
        attachmentDTO2.setTitle("updatedTitle1");
        attachmentController.save(attachmentDTO);
        attachmentController.save(attachmentDTO1);
        log.info("Attachments: " + attachmentController.getAll());
        attachmentController.delete(2);
        attachmentController.update(1, attachmentDTO2);
        log.info("Attachments: " + attachmentController.getAll());
        //Comment
        CommentController commentController = context.getBean(CommentController.class);
        CommentDTO commentDTO = new CommentDTO();
        CommentDTO commentDTO1 = new CommentDTO();
        CommentDTO commentDTO2 = new CommentDTO();
        commentDTO.setId(1).setText("comment1");
        commentDTO1.setId(2).setText("comment2");
        commentDTO2.setId(2).setText("updatedComment1");
        commentController.save(commentDTO);
        commentController.save(commentDTO1);
        log.info("Comments: " + commentController.getAll());
        commentController.delete(2);
        commentController.update(1, commentDTO2);
        log.info("Comments: " + commentController.getAll());
        //Project
        ProjectController projectController = context.getBean(ProjectController.class);
        ProjectDTO projectDTO = new ProjectDTO();
        ProjectDTO projectDTO1 = new ProjectDTO();
        ProjectDTO projectDTO2 = new ProjectDTO();
        projectDTO.setId(1).setDescription("description1").setStatus("status1").setTitle("title1").setStartDate(LocalDateTime.now()).setManagerId(1).setTeamId(1);
        projectDTO1.setId(2).setDescription("description2").setStatus("status2").setTitle("title2").setStartDate(LocalDateTime.now()).setManagerId(2).setTeamId(2);
        projectDTO2.setDescription("updatedDescription1").setStatus("updatedStatus1").setTitle("updatedTitle1").setStartDate(LocalDateTime.now()).setManagerId(11).setTeamId(11);
        projectController.save(projectDTO);
        projectController.save(projectDTO1);
        log.info("Projects: " + projectController.getAll());
        projectController.delete(2);
        projectController.update(1, projectDTO2);
        log.info("Projects:" + projectController.getAll());
        //Report
        ReportController reportController = context.getBean(ReportController.class);
        ReportDTO reportDTO = new ReportDTO();
        ReportDTO reportDTO1 = new ReportDTO();
        ReportDTO reportDTO2 = new ReportDTO();
        reportDTO.setId(1).setText("text1").setTitle("title1").setTaskId(1).setUserId(1).setCreatedAt(LocalDateTime.now());
        reportDTO1.setId(2).setText("text2").setTitle("title2").setTaskId(2).setUserId(2).setCreatedAt(LocalDateTime.now());
        reportDTO2.setText("updatedText1").setTitle("updatedTitle1");
        reportController.save(reportDTO);
        reportController.save(reportDTO1);
        log.info("Reports: " + reportController.getAll());
        reportController.delete(2);
        reportController.update(1, reportDTO2);
        log.info("Reports " + reportController.getAll());
        //Role
        RoleController roleController = context.getBean(RoleController.class);
        RoleDTO roleDTO = new RoleDTO();
        RoleDTO roleDTO1 = new RoleDTO();
        RoleDTO roleDTO2 = new RoleDTO();
        roleDTO.setId(1).setName("name1");
        roleDTO1.setId(2).setName("name2");
        roleDTO2.setName("updatedName1");
        roleController.save(roleDTO);
        roleController.save(roleDTO1);
        log.info("Roles: " + roleController.getAll());
        roleController.delete(2);
        roleController.update(1, roleDTO2);
        log.info("Roles: " + roleController.getAll());
        //Task
        TaskController taskController = context.getBean(TaskController.class);
        TaskDTO taskDTO = new TaskDTO();
        TaskDTO taskDTO1 = new TaskDTO();
        TaskDTO taskDTO2 = new TaskDTO();
        taskDTO.setId(1).setDescription("description1").setStatus("status1").setTitle("title1").
                setStartDate(LocalDateTime.now()).setAssignee(1).setProjectId(1).setCategory("category1").
                setDueDate(LocalDateTime.now()).setLabel("label1").setPriority("priority1").setReporter(1);

        taskDTO1.setId(2).setDescription("description2").setStatus("status2").setTitle("title2").
                setStartDate(LocalDateTime.now()).setAssignee(2).setProjectId(2).setCategory("category2").
                setDueDate(LocalDateTime.now()).setLabel("label2").setPriority("priority2").setReporter(2);

        taskDTO2.setDescription("updatedDescription1").setStatus("updatedStatus1").setTitle("updatedTitle1").
                setCategory("updatedCategory1").setDueDate(LocalDateTime.now()).setLabel("updatedLabel1").
                setPriority("updatedPriority1");
        taskController.save(taskDTO);
        taskController.save(taskDTO1);
        log.info("Tasks: " + taskController.getAll());
        taskController.delete(2);
        taskController.update(1, taskDTO2);
        log.info("Tasks: " + taskController.getAll());
        //Team
        TeamController teamController = context.getBean(TeamController.class);
        TeamDTO teamDTO = new TeamDTO();
        TeamDTO teamDTO1 = new TeamDTO();
        TeamDTO teamDTO2 = new TeamDTO();
        teamDTO.setId(1).setName("name1");
        teamDTO1.setId(2).setName("name2");
        teamDTO2.setName("updatedName1");
        teamController.save(teamDTO);
        teamController.save(teamDTO1);
        log.info("Teams: " + teamController.getAll());
        teamController.delete(2);
        teamController.update(1, teamDTO2);
        log.info("Teams: " + teamController.getAll());
        //UserDetails
        UserDetailsController userDetailsController = context.getBean(UserDetailsController.class);
        UserDetailsDTO userDetailsDTO = new UserDetailsDTO();
        UserDetailsDTO userDetailsDTO1 = new UserDetailsDTO();
        UserDetailsDTO userDetailsDTO2 = new UserDetailsDTO();
        userDetailsDTO.setId(1).setName("name1").setSurname("surname1").setDepartment("department1").setPhone("phone1").
                setWorkPhone("workPhone1").setWorkAddress("workAddress1");
        userDetailsDTO1.setId(2).setName("name2").setSurname("surname2").setDepartment("department2").setPhone("phone2").
                setWorkPhone("workPhone2").setWorkAddress("workAddress2");
        userDetailsDTO2.setName("updatedName1").setSurname("updatedSurname1").setDepartment("updatedDepartment1").
                setPhone("updatedPhone1").setWorkPhone("updatedWorkPhone1").setWorkAddress("updatedWorkAddress1");
        userDetailsController.save(userDetailsDTO);
        userDetailsController.save(userDetailsDTO1);
        log.info("UserDetails: " + userDetailsController.getAll());
        userDetailsController.delete(2);
        userDetailsController.update(1, userDetailsDTO2);
        log.info("UserDetails: " + userDetailsController.getAll());
        //User
        UserController userController = context.getBean(UserController.class);
        UserDTO userDTO = new UserDTO();
        UserDTO userDTO1 = new UserDTO();
        UserDTO userDTO2 = new UserDTO();
        userDTO.setId(1).setUsername("username1").setPassword("password1").setEmail("email1");
        userDTO1.setId(2).setUsername("username2").setPassword("password2").setEmail("email2");
        userDTO2.setUsername("updatedUsername1").setPassword("updatedPassword1").setEmail("UpdatedEmail1");
        userController.save(userDTO);
        userController.save(userDTO1);
        log.info("Users: " + userController.getAll());
        userController.delete(2);
        userController.update(1, userDTO2);
        log.info("Users: " + userController.getAll());
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();

    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }
}