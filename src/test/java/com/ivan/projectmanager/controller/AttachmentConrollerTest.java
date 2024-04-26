package com.ivan.projectmanager.controller;

import com.ivan.projectmanager.dto.AttachmentDTO;
import com.ivan.projectmanager.service.AttachmentService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.HandlerMapping;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = TestControllerConfiguration.class)
@WebAppConfiguration
@Transactional
public class AttachmentConrollerTest {
@Autowired
AttachmentService attachmentService;
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @Sql(scripts = {"classpath:data/attachmentrepositorytests/delete-attachments.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(scripts = {"classpath:data/attachmentrepositorytests/insert-attachments.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testGetAllAttachments() throws Exception {
       mockMvc.perform(get("/projects/1/tasks/1/attachments")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title").value("Test Attachment"));
    }

    @Test
    @Sql(scripts = {"classpath:data/attachmentrepositorytests/delete-attachments.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(scripts = {"classpath:data/attachmentrepositorytests/insert-attachments.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testGetAttachmentById() throws Exception {
         mockMvc.perform(get("/projects/1/tasks/1/attachments/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Test Attachment"))
              .andReturn();
    }

    @Test
    @Sql(scripts = {"classpath:data/attachmentrepositorytests/delete-attachments.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testSaveAttachment() throws Exception {
        String requestBody = "{\"title\": \"Attachment\", \"taskId\": 1}";
        mockMvc.perform(post("/projects/1/tasks/1/attachments/new")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Attachment"));
    }
    @Test
    @Sql(scripts = {"classpath:data/attachmentrepositorytests/delete-attachments.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(scripts = {"classpath:data/attachmentrepositorytests/insert-attachments.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testUpdateAttachment() throws Exception {
        String requestBody = "{\"title\": \"Updated Attachment\", \"taskId\": 1}";
      mockMvc.perform(patch("/projects/1/tasks/1/attachments/1")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Updated Attachment"));
    }

    @Test
    @Sql(scripts = {"classpath:data/attachmentrepositorytests/delete-attachments.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(scripts = {"classpath:data/attachmentrepositorytests/insert-attachments.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testDeleteAttachment() throws Exception {
        mockMvc.perform(delete("/projects/1/tasks/1/attachments/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        }
    }

