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
public class ReportControllerTest {
    private MockMvc mockMvc;

    @BeforeEach
    void setUp(WebApplicationContext wac) {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).apply(springSecurity()).build();
    }

    @WithMockUser(username = "username", roles = {"USER"})
    @Test
    @Sql("classpath:data/reportrepositorytests/insert-reports.sql")
    void testGetAllReports() throws Exception {
        mockMvc.perform(get("/projects/1/tasks/1/reports")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].title").value("title"))
                .andExpect(jsonPath("$.content[0].text").value("text"))
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.totalPages").value(1));

    }

    @WithMockUser(username = "username", roles = {"ADMIN"})
    @Test
    @Sql("classpath:data/reportrepositorytests/insert-reports.sql")
    void testGetReportById() throws Exception {
        mockMvc.perform(get("/projects/1/tasks/1/reports/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("title"))
                .andExpect(jsonPath("$.text").value("text"));
    }

    @WithMockUser(username = "username", roles = {"USER"})
    @Test
    @Sql("classpath:data/taskrepositorytests/insert-tasks.sql")
    void testSaveReport() throws Exception {
        String requestBody = "{\"title\": \"saved title\", \"text\":\"saved text\",\"createAt\": \"2024-04-25T20:01:46.488778\",\"taskId\": 1,\"userId\":1}";
        mockMvc.perform(post("/projects/1/tasks/1/reports")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("saved title"))
                .andExpect(jsonPath("$.text").value("saved text"));

    }

    @WithMockUser(username = "username", roles = {"ADMIN"})
    @Test
    @Sql("classpath:data/reportrepositorytests/insert-reports.sql")
    void testUpdateReport() throws Exception {
        String requestBody = "{\"title\": \"updated title\", \"text\":\"updated text\"}";
        mockMvc.perform(put("/projects/1/tasks/1/reports/1")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("updated title"))
                .andExpect(jsonPath("$.text").value("updated text"));
    }

    @WithMockUser(username = "username", roles = {"USER"})
    @Test
    @Sql("classpath:data/reportrepositorytests/insert-reports.sql")
    void testDeleteReport() throws Exception {
        mockMvc.perform(delete("/projects/1/tasks/1/reports/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @WithMockUser(username = "username", roles = {"MANAGER"})
    @Test
    public void testAccessDenied() throws Exception {
        String requestBody = "{\"title\": \"saved title\", \"text\":\"saved text\",\"createAt\": \"2024-04-25T20:01:46.488778\",\"taskId\": 1,\"userId\":1}";
        mockMvc.perform(post("/projects/1/tasks/1/reports")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string("Internal Server Error: Access Denied"));
    }

    @Test
    public void testUnregisteredUserAccessDenied() throws Exception {
        String requestBody = "{\"title\": \"saved title\", \"text\":\"saved text\",\"createAt\": \"2024-04-25T20:01:46.488778\",\"taskId\": 1,\"userId\":1}";
        mockMvc.perform(post("/projects/1/tasks/1/reports")
                        .with(anonymous())
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}