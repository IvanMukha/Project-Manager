package com.ivan.projectmanager.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

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
public class AttachmentControllerTest {
    private MockMvc mockMvc;

    @BeforeEach
    void setUp(WebApplicationContext wac) {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    @Sql("classpath:data/attachmentrepositorytests/insert-attachments.sql")
    public void testGetAllAttachments() throws Exception {
        mockMvc.perform(get("/projects/1/tasks/1/attachments")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title").value("Test Attachment"));
    }

    @Test
    @Sql("classpath:data/attachmentrepositorytests/insert-attachments.sql")
    public void testGetAttachmentById() throws Exception {
        mockMvc.perform(get("/projects/1/tasks/1/attachments/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Test Attachment"))
                .andReturn();
    }

    @Test
    @Sql("classpath:data/taskrepositorytests/insert-tasks.sql")
    public void testSaveAttachment() throws Exception {
        String requestBody = "{\"title\": \"Attachment\",\"path\": \"path\"}";
        mockMvc.perform(post("/projects/1/tasks/1/attachments")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Attachment"));
    }

    @Test
    @Sql("classpath:data/attachmentrepositorytests/insert-attachments.sql")
    public void testUpdateAttachment() throws Exception {
        String requestBody = "{\"title\": \"Updated Attachment\", \"taskId\": 1}";
        mockMvc.perform(put("/projects/1/tasks/1/attachments/1")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Updated Attachment"));
    }

    @Test
    @Sql("classpath:data/attachmentrepositorytests/insert-attachments.sql")
    public void testDeleteAttachment() throws Exception {
        mockMvc.perform(delete("/projects/1/tasks/1/attachments/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}

