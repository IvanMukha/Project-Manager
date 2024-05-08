package com.ivan.projectmanager.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestControllerConfiguration.class)
@WebAppConfiguration
public class TaskControllerTest {
    private MockMvc mockMvc;

    @BeforeEach
    void setUp(WebApplicationContext wac) {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).apply(springSecurity()).build();
    }

    @WithMockUser(username = "username", roles = {"USER"})
    @Test
    @Sql("classpath:data/taskrepositorytests/insert-tasks.sql")
    void testGetAllTasks() throws Exception {
        mockMvc.perform(get("/projects/1/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title").value("Task 1"))
                .andExpect(jsonPath("$[0].status").value("In progress"))
                .andExpect(jsonPath("$[0].priority").value("High"))
                .andExpect(jsonPath("$[0].category").value("Development"));
    }

    @WithMockUser(username = "username", roles = {"USER"})
    @Test
    @Sql("classpath:data/taskrepositorytests/insert-tasks.sql")
    void testGetTaskById() throws Exception {
        mockMvc.perform(get("/projects/1/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Task 1"))
                .andExpect(jsonPath("$.status").value("In progress"))
                .andExpect(jsonPath("$.priority").value("High"))
                .andExpect(jsonPath("$.category").value("Development"));
    }

    @WithMockUser(username = "username", roles = {"ADMIN"})
    @Test
    @Sql("classpath:data/taskrepositorytests/insert-tasks-save.sql")
    void testSaveTask() throws Exception {
        String requestBody = "{\"title\": \"Task 1\", \"status\": \"In progress\", \"priority\": \"High\", \"startDate\":" +
                " \"2024-04-17T10:00:00\", " +
                "\"dueDate\": \"2024-04-20T17:00:00\", \"reporterId\": 1, \"assigneeId\": 2, " +
                "\"category\": \"Development\", \"label\": \"Bug\", \"description\": \"Description of the task\", " +
                "\"projectId\": 1}";
        mockMvc.perform(post("/projects/1/tasks")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Task 1"))
                .andExpect(jsonPath("$.status").value("In progress"))
                .andExpect(jsonPath("$.priority").value("High"))
                .andExpect(jsonPath("$.category").value("Development"))
                .andExpect(jsonPath("$.label").value("Bug"));
    }

    @WithMockUser(username = "username", roles = {"ADMIN"})
    @Test
    @Sql("classpath:data/taskrepositorytests/insert-tasks.sql")
    void testUpdateTask() throws Exception {
        String requestBody = "{\"title\": \"updated Task 1\", \"status\": \"updated status\", \"priority\": \"updated priority\"}";
        mockMvc.perform(put("/projects/1/tasks/1")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("updated Task 1"))
                .andExpect(jsonPath("$.status").value("updated status"))
                .andExpect(jsonPath("$.priority").value("updated priority"));
    }

    @WithMockUser(username = "username", roles = {"ADMIN"})
    @Test
    @Sql("classpath:data/taskrepositorytests/insert-tasks.sql")
    void testDeleteTask() throws Exception {
        mockMvc.perform(delete("/projects/1/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @WithMockUser(username = "username", roles = {"USER"})
    @Test
    public void testAccessDenied() throws Exception {
        String requestBody = "{\"title\": \"Task 1\", \"status\": \"In progress\", \"priority\": \"High\", \"startDate\":" +
                " \"2024-04-17T10:00:00\", " +
                "\"dueDate\": \"2024-04-20T17:00:00\", \"reporterId\": 1, \"assigneeId\": 2, " +
                "\"category\": \"Development\", \"label\": \"Bug\", \"description\": \"Description of the task\", " +
                "\"projectId\": 1}";
        mockMvc.perform(post("/projects/1/tasks")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string("Internal Server Error: Access Denied"));
    }

    @Test
    public void testUnregisteredUserAccessDenied() throws Exception {
        String requestBody = "{\"title\": \"Task 1\", \"status\": \"In progress\", \"priority\": \"High\", \"startDate\":" +
                " \"2024-04-17T10:00:00\", " +
                "\"dueDate\": \"2024-04-20T17:00:00\", \"reporterId\": 1, \"assigneeId\": 2, " +
                "\"category\": \"Development\", \"label\": \"Bug\", \"description\": \"Description of the task\", " +
                "\"projectId\": 1}";
        mockMvc.perform(post("/projects/1/tasks")
                        .with(anonymous())
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}