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
public class UserControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @Sql(scripts = {"classpath:data/userrepositorytests/delete-users.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(scripts = {"classpath:data/userrepositorytests/insert-users.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void testGetAllUsers() throws Exception {
        mockMvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].username").value("user1"))
                .andExpect(jsonPath("$[0].password").value("password1"))
                .andExpect(jsonPath("$[0].email").value("email1@gmail.com"))
                .andExpect(jsonPath("$[1].username").value("user2"))
                .andExpect(jsonPath("$[1].password").value("password2"))
                .andExpect(jsonPath("$[1].email").value("email2@gmail.com"));
    }

    @Test
    @Sql(scripts = {"classpath:data/userrepositorytests/delete-users.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(scripts = {"classpath:data/userrepositorytests/insert-users.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void testGetUserById() throws Exception {
        mockMvc.perform(get("/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("user1"))
                .andExpect(jsonPath("$.password").value("password1"))
                .andExpect(jsonPath("$.email").value("email1@gmail.com"));
    }

    @Test
    @Sql(scripts = {"classpath:data/userrepositorytests/delete-users.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testSaveUser() throws Exception {
        String requestBody = "{\"username\": \"saved username\", \"password\": \"saved password\", \"email\": \"saved email\"}";
        mockMvc.perform(post("/users/new")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("saved username"))
                .andExpect(jsonPath("$.password").value("saved password"))
                .andExpect(jsonPath("$.email").value("saved email"));

    }

    @Test
    @Sql(scripts = {"classpath:data/userrepositorytests/delete-users.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(scripts = {"classpath:data/userrepositorytests/insert-users.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void testUpdateUser() throws Exception {
        String requestBody = "{\"username\": \"updated username\", \"password\": \"updated password\", \"email\": \"updated email\"}";
        mockMvc.perform(patch("/users/1")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("updated username"))
                .andExpect(jsonPath("$.password").value("updated password"))
                .andExpect(jsonPath("$.email").value("updated email"));
    }

    @Test
    @Sql(scripts = {"classpath:data/userrepositorytests/delete-users.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(scripts = {"classpath:data/userrepositorytests/insert-users.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
