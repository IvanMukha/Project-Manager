package com.ivan.projectmanager.service;

import com.ivan.projectmanager.dto.AttachmentDTO;
import com.ivan.projectmanager.model.Attachment;
import com.ivan.projectmanager.model.Task;
import com.ivan.projectmanager.repository.AttachmentRepository;
import com.ivan.projectmanager.service.impl.AttachmentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = TestServiceConfiguration.class)
//@WebAppConfiguration
public class AttachmentServiceImplTest {
    @Mock
    private AttachmentRepository attachmentRepository;
    @InjectMocks
    private AttachmentServiceImpl attachmentService;
    @Mock
    private ModelMapper modelMapper;

    @Test
    public void testGetAll() {
        Attachment attachment = new Attachment();
        attachment.setId(1L).setTitle("title1");
        Attachment attachment2 = new Attachment();
        attachment2.setId(2L).setTitle("title2");
        Mockito.when(attachmentRepository.getAll()).thenReturn(List.of(attachment, attachment2));
        List<AttachmentDTO> result = attachmentService.getAll();
        assertEquals(2, result.size());
        assertEquals(attachment.getTitle(), result.get(0).getTitle());
        assertEquals(attachment2.getTitle(), result.get(1).getTitle());
        verify(attachmentRepository).getAll();
    }

    @Test
    public void testSave() {
        AttachmentDTO attachmentDTO = new AttachmentDTO();
        Task task = new Task();
        task.setId(1L).setTitle("title1");
        attachmentDTO.setTitle("New Attachment").setTask(task);
        Attachment attachment = new Attachment();
        Mockito.when(attachmentRepository.save(attachment)).thenReturn(attachment);
        AttachmentDTO attachmentDTO1 = attachmentService.save(attachmentDTO);
        assertNotNull(attachmentDTO1);
        assertEquals(attachmentDTO.getTitle(), attachmentDTO1.getTitle());
        verify(attachmentRepository).save(any());
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
        verify(attachmentRepository.getById(1L));
    }

    @Test
    void testUpdate() {
        Attachment attachment = new Attachment();
        attachment.setTitle("title");
        AttachmentDTO attachmentDTO = new AttachmentDTO();
        attachmentDTO.setTitle("title");
        when(attachmentRepository.update(1L, attachment)).thenReturn(Optional.of(attachment));
        Optional<AttachmentDTO> result = attachmentService.update(1L, attachmentDTO);
        assertTrue(result.isPresent());
        assertEquals(attachment.getTitle(), result.get().getTitle());
        verify(attachmentRepository).update(1L, attachment);
    }

    @Test
    void testDelete() {
        Long id = 1L;
        attachmentService.delete(id);
        verify(attachmentRepository).delete(id);
    }
}
