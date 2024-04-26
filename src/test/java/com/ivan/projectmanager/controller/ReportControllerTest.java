package com.ivan.projectmanager.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = TestControllerConfiguration.class)
@WebAppConfiguration
public class ReportControllerTest {
        @Autowired
        private WebApplicationContext webApplicationContext;

        private MockMvc mockMvc;

        @BeforeEach
        void setUp() {
            mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        }

        @Test
        @Sql(scripts = {"classpath:data/reportrepositorytests/delete-reports.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
        @Sql(scripts = {"classpath:data/reportrepositorytests/insert-reports.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        void testGetAllProjects() throws Exception {
            mockMvc.perform(get("/projects/1}/tasks/1/reports")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$[0].title").value("title"))
                    .andExpect(jsonPath("$[0].text").value("text"));
        }

        @Test
        @Sql(scripts = {"classpath:data/reportrepositorytests/delete-reports.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
        @Sql(scripts = {"classpath:data/reportrepositorytests/insert-reports.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        void testGetProjectById() throws Exception {
                mockMvc.perform(get("/projects/1/tasks/1/reports/1")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.title").value("title"))
                    .andExpect(jsonPath("$.text").value("text"));
        }

        @Test
        @Sql(scripts = {"classpath:data/reportrepositorytests/delete-reports.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
        void testSaveProject() throws Exception {
            String requestBody = "{\"title\": \"saved title\", \"text\":\"saved text\",\"createAt\": \"2024-04-25T20:01:46.488778\",\"taskId\": 1,\"userId\":1}";
                mockMvc.perform(post("/projects/1/tasks/1/reports/new")
                            .content(requestBody)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.title").value("saved title"))
                    .andExpect(jsonPath("$.text").value("saved text"));

        }

        @Test
        @Sql(scripts = {"classpath:data/reportrepositorytests/delete-reports.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
        @Sql(scripts = {"classpath:data/reportrepositorytests/insert-reports.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        void testUpdateProject() throws Exception {
            String requestBody = "{\"title\": \"updated title\", \"text\":\"updated text\"}";
            mockMvc.perform(patch("/projects/1/tasks/1/reports/1")
                            .content(requestBody)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.title").value("updated title"))
                    .andExpect(jsonPath("$.text").value("updated text"));
        }

        @Test
        @Sql(scripts = {"classpath:data/commentrepositorytests/delete-comments.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
        @Sql(scripts = {"classpath:data/commentrepositorytests/insert-comments.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        void testDeleteProject() throws Exception {
            mockMvc.perform(delete("/projects/1/tasks/1/reports/1")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent());
        }
    }