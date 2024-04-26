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
public class UserDetailsControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    @Test
    @Sql(scripts = {"classpath:data/userdetailsrepositorytests/delete-userdetails.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(scripts = {"classpath:data/userdetailsrepositorytests/insert-userdetails.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
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

    @Test
    @Sql(scripts = {"classpath:data/userdetailsrepositorytests/delete-userdetails.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testSaveUser() throws Exception {
        String requestBody = "{\"userId\":1, \"name\":\"saved name\", \"surname\":\"saved surname\", \"phone\":\"saved phone\"" +
                ", \"workPhone\":\"saved workPhone\", \"workAddress\":\"saved workAddress\", \"department\":\"saved department\"}";
        mockMvc.perform(post("/users/1/userDetails/new")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("saved name"))
                .andExpect(jsonPath("$.surname").value("saved surname"))
                .andExpect(jsonPath("$.phone").value("saved phone"))
                .andExpect(jsonPath("$.workPhone").value("saved workPhone"));
    }

    @Test
    @Sql(scripts = {"classpath:data/userdetailsrepositorytests/delete-userdetails.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(scripts = {"classpath:data/userdetailsrepositorytests/insert-userdetails.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void testUpdateUser() throws Exception {
        String requestBody = "{\"userId\":1, \"name\":\"updated name\", \"surname\":\"updated surname\", \"phone\":\"updated phone\"" +
                ", \"workPhone\":\"updated workPhone\", \"workAddress\":\"updated workAddress\", \"department\":\"updated department\"}";
        mockMvc.perform(patch("/users/1/userDetails")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("updated name"))
                .andExpect(jsonPath("$.surname").value("updated surname"))
                .andExpect(jsonPath("$.phone").value("updated phone"))
                .andExpect(jsonPath("$.workPhone").value("updated workPhone"));
    }

    @Test
    @Sql(scripts = {"classpath:data/userdetailsrepositorytests/delete-userdetails.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(scripts = {"classpath:data/userdetailsrepositorytests/insert-userdetails.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/users/1/userDetails")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}

