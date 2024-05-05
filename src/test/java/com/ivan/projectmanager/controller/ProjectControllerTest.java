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
public class ProjectControllerTest {
    private MockMvc mockMvc;

    @BeforeEach
    void setUp(WebApplicationContext wac) {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).apply(springSecurity()).build();
    }

    @WithMockUser(username = "username", roles = {"ADMIN"})
    @Test
    @Sql("classpath:data/projectrepositorytests/insert-projects.sql")
    void testGetAllProjects() throws Exception {
        mockMvc.perform(get("/projects")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title").value("Project ABC"))
                .andExpect(jsonPath("$[0].description").value("Description of Project ABC"));
    }

    @WithMockUser(username = "username", roles = {"ADMIN"})
    @Test
    @Sql("classpath:data/projectrepositorytests/insert-projects.sql")
    void testGetProjectById() throws Exception {
        mockMvc.perform(get("/projects/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Project ABC"))
                .andExpect(jsonPath("$.description").value("Description of Project ABC"));

    }

    @WithMockUser(username = "username", roles = {"ADMIN"})
    @Test
    @Sql("classpath:data/projectrepositorytests/insert-projects.sql")
    void testSaveProject() throws Exception {
        String requestBody = "{\"title\": \"saved title\", \"description\":\"saved description\",\"startDate\": \"2024-04-25T20:01:46.488778\"}";
        mockMvc.perform(post("/projects")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("saved title"))
                .andExpect(jsonPath("$.description").value("saved description"));
    }

    @WithMockUser(username = "username", roles = {"ADMIN"})
    @Test
    @Sql("classpath:data/projectrepositorytests/insert-projects.sql")
    void testUpdateProject() throws Exception {
        String requestBody = "{\"title\": \"updated title\", \"description\":\"updated description\"}";
        mockMvc.perform(put("/projects/1")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("updated title"))
                .andExpect(jsonPath("$.description").value("updated description"));
    }

    @WithMockUser(username = "username", roles = {"ADMIN"})
    @Test
    @Sql("classpath:data/projectrepositorytests/insert-projects.sql")
    void testDeleteProject() throws Exception {
        mockMvc.perform(delete("/projects/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
