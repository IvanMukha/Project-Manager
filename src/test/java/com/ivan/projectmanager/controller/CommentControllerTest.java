package com.ivan.projectmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivan.projectmanager.dto.CommentDTO;
import com.ivan.projectmanager.service.CommentService;
import org.hibernate.annotations.processing.SQL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
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

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
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
public class CommentControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @Sql(scripts = {"classpath:data/commentrepositorytests/delete-comments.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(scripts = {"classpath:data/commentrepositorytests/insert-comments.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void testGetAllComments() throws Exception {
    mockMvc.perform(get("/projects/1/tasks/1/comments")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].text").value("text"));
    }

    @Test
    @Sql(scripts = {"classpath:data/commentrepositorytests/delete-comments.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(scripts = {"classpath:data/commentrepositorytests/insert-comments.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void testGetCommentById() throws Exception {
        mockMvc.perform(get("/projects/1/tasks/1/comments/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.text").value("text"));

    }

    @Test
    @Sql(scripts = {"classpath:data/commentrepositorytests/delete-comments.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testSaveComment() throws Exception {
        String requestBody = "{\"text\": \"saveText\", \"addtime\":\"2024-04-25 20:01:46.488778\"}";
            mockMvc.perform(post("/projects/1/tasks/1/comments/new")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.text").value("saveText"))
                .andReturn();
    }

    @Test
    @Sql(scripts = {"classpath:data/commentrepositorytests/delete-comments.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(scripts = {"classpath:data/commentrepositorytests/insert-comments.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void testUpdateComment() throws Exception {
        String requestBody = "{\"text\": \"updated text\", \"addtime\":\"2024-04-25 20:01:46.488778\"}";
        mockMvc.perform(patch("/projects/1/tasks/1/comments/1")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.text").value("updated text"));
    }

    @Test
    @Sql(scripts = {"classpath:data/commentrepositorytests/delete-comments.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(scripts = {"classpath:data/commentrepositorytests/insert-comments.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void testDeleteComment() throws Exception {
        mockMvc.perform(delete("/projects/1/tasks/1/comments/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
