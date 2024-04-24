package com.ivan.projectmanager.service;

import com.ivan.projectmanager.dto.AttachmentDTO;
import com.ivan.projectmanager.model.Attachment;
import com.ivan.projectmanager.model.Task;
import com.ivan.projectmanager.repository.AttachmentRepository;
import com.ivan.projectmanager.service.impl.AttachmentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestServiceConfiguration.class)
@RunWith(MockitoJUnitRunner.class)
public class AttachmentServiceImplTest {


    @InjectMocks
    private AttachmentServiceImpl attachmentService;

    @Mock
    private AttachmentRepository attachmentRepository;

    @Test
    @Sql(scripts = {"classpath:data/attachmentrepositorytests/delete-attachments.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql("classpath:data/attachmentrepositorytests/insert-attachments.sql")
    public void testGetAll() {
    }

    @Test
    void testSave() {
        Task task = new Task();
        task.setId(1L);
        AttachmentDTO attachmentDTO = new AttachmentDTO();
        attachmentDTO.setTitle("New Attachment");
        attachmentDTO.setTaskId(1L);
        Attachment attachment = new Attachment();
        attachment.setTitle("New Attachment");
        attachment.setTask(task);
        when(attachmentRepository.save(any(Attachment.class))).thenReturn(attachment);
        AttachmentDTO result = attachmentService.save(attachmentDTO);
        assertNotNull(result);
        assertEquals(attachmentDTO.getTitle(), result.getTitle());
        assertEquals(attachmentDTO.getTaskId(), result.getTaskId());
    }

    @Test
    void testGetById() {
        long id = 1L;
        Attachment attachment = new Attachment();
        attachment.setId(id);
        attachment.setTitle("Attachment");
        when(attachmentRepository.getById(id)).thenReturn(Optional.of(attachment));
        Optional<AttachmentDTO> result = attachmentService.getById(id);
        assertTrue(result.isPresent());
        assertEquals(attachment.getTitle(), result.get().getTitle());
    }
}
