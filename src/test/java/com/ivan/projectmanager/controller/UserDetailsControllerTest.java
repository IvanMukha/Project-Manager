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
public class UserDetailsControllerTest {
    private MockMvc mockMvc;

    @BeforeEach
    void setUp(WebApplicationContext wac) {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).apply(springSecurity()).build();
    }

    @WithMockUser(username = "username", roles = {"USER"})
    @Test
    @Sql("classpath:data/userdetailsrepositorytests/insert-userdetails.sql")
    void testGetUserById() throws Exception {
        mockMvc.perform(get("/users/1/userDetails")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("name"))
                .andExpect(jsonPath("$.surname").value("surname"))
                .andExpect(jsonPath("$.phone").value("phone"))
                .andExpect(jsonPath("$.workPhone").value("workPhone"))
                .andExpect(jsonPath("$.workAdress").value("workAddress"))
                .andExpect(jsonPath("$.department").value("department"));
    }

    @WithMockUser(username = "username", roles = {"USER"})
    @Test
    @Sql("classpath:data/userrepositorytests/insert-users.sql")
    void testSaveUser() throws Exception {
        String requestBody = "{\"userId\":1, \"name\":\"saved name\", \"surname\":\"saved surname\", \"phone\":\"saved phone\"" +
                ", \"workPhone\":\"saved workPhone\", \"workAddress\":\"saved workAddress\", \"department\":\"saved department\"}";
        mockMvc.perform(post("/users/1/userDetails")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("saved name"))
                .andExpect(jsonPath("$.surname").value("saved surname"))
                .andExpect(jsonPath("$.phone").value("saved phone"))
                .andExpect(jsonPath("$.workPhone").value("saved workPhone"));
    }

    @WithMockUser(username = "username", roles = {"ADMIN"})
    @Test
    @Sql("classpath:data/userdetailsrepositorytests/insert-userdetails.sql")
    void testUpdateUser() throws Exception {
        String requestBody = "{\"userId\":1, \"name\":\"updated name\", \"surname\":\"updated surname\", \"phone\":\"updated phone\"" +
                ", \"workPhone\":\"updated workPhone\", \"workAddress\":\"updated workAddress\", \"department\":\"updated department\"}";
        mockMvc.perform(put("/users/1/userDetails")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("updated name"))
                .andExpect(jsonPath("$.surname").value("updated surname"))
                .andExpect(jsonPath("$.phone").value("updated phone"))
                .andExpect(jsonPath("$.workPhone").value("updated workPhone"));
    }

    @WithMockUser(username = "username", roles = {"USER"})
    @Test
    @Sql("classpath:data/userdetailsrepositorytests/insert-userdetails.sql")
    void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/users/1/userDetails")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @WithMockUser(username = "username", roles = {"MANAGER"})
    @Test
    public void testAccessDenied() throws Exception {
        String requestBody = "{\"userId\":1, \"name\":\"saved name\", \"surname\":\"saved surname\", \"phone\":\"saved phone\"" +
                ", \"workPhone\":\"saved workPhone\", \"workAddress\":\"saved workAddress\", \"department\":\"saved department\"}";
        mockMvc.perform(post("/users/1/userDetails")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string("Internal Server Error: Access Denied"));
    }

    @Test
    public void testUnregisteredUserAccessDenied() throws Exception {
        String requestBody = "{\"userId\":1, \"name\":\"saved name\", \"surname\":\"saved surname\", \"phone\":\"saved phone\"" +
                ", \"workPhone\":\"saved workPhone\", \"workAddress\":\"saved workAddress\", \"department\":\"saved department\"}";
        mockMvc.perform(post("/users/1/userDetails")
                        .with(anonymous())
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}

