package com.ivan.projectmanager.service;

import com.ivan.projectmanager.dto.AttachmentDTO;
import com.ivan.projectmanager.model.Attachment;
import com.ivan.projectmanager.model.Project;
import com.ivan.projectmanager.model.Task;
import com.ivan.projectmanager.repository.AttachmentRepository;
import com.ivan.projectmanager.repository.TaskRepository;
import com.ivan.projectmanager.service.impl.AttachmentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AttachmentServiceImplTest {
    @Mock
    private AttachmentRepository attachmentRepository;
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private AttachmentServiceImpl attachmentService;


    @Test
    public void testGetAll() {
        Project project = new Project();
        project.setId(1L).setTitle("project");
        Task task = new Task().setId(1L).setTitle("task").setProject(project);
        Attachment attachment = new Attachment().setId(1L).setTitle("title1").setTask(task);
        Attachment attachment2 = new Attachment().setId(2L).setTitle("title2").setTask(task);
        AttachmentDTO attachmentDTO = new AttachmentDTO().setId(1L).setTitle("title1").setTaskId(1L);
        AttachmentDTO attachmentDTO2 = new AttachmentDTO().setId(2L).setTitle("title2").setTaskId(1L);

        when(modelMapper.map(attachment, AttachmentDTO.class)).thenReturn(attachmentDTO);
        when(modelMapper.map(attachment2, AttachmentDTO.class)).thenReturn(attachmentDTO2);
        Page<Attachment> attachmentPage = new PageImpl<>(List.of(attachment, attachment2));
        Mockito.when(attachmentRepository.getAll(1L, 1L, PageRequest.of(0, 10))).thenReturn(attachmentPage);

        Page<AttachmentDTO> resultPage = attachmentService.getAll(1L, 1L, 0, 10);
        List<AttachmentDTO> result = resultPage.getContent();
        assertEquals(2, result.size());
        assertEquals(attachment.getTitle(), result.get(0).getTitle());
        assertEquals(attachment2.getTitle(), result.get(1).getTitle());

        verify(attachmentRepository).getAll(1L, 1L, PageRequest.of(0, 10));
    }

    @Test
    public void testSave() {
        AttachmentDTO attachmentDTO = new AttachmentDTO().setId(1L).setTitle("New Attachment").setTaskId(1L);
        Attachment attachment = new Attachment().setId(1L).setTitle("title1");
        Project project = new Project().setId(1L).setTitle("project");
        Task task = new Task().setId(1L).setTitle("task").setProject(project);

        when(modelMapper.map(attachmentDTO, Attachment.class)).thenReturn(attachment);
        when(modelMapper.map(attachment, AttachmentDTO.class)).thenReturn(attachmentDTO);
        when(taskRepository.getById(1L, 1L)).thenReturn(Optional.ofNullable(task));
        when(attachmentRepository.save(attachment)).thenReturn(attachment);

        AttachmentDTO attachmentDTO1 = attachmentService.save(1L, 1L, attachmentDTO);
        assertNotNull(attachmentDTO1);
        assertEquals(attachmentDTO.getTitle(), attachmentDTO1.getTitle());
        verify(attachmentRepository).save(any());
    }

    @Test
    void testGetById() {
        Project project = new Project().setId(1L).setTitle("project");
        Task task = new Task().setId(1L).setTitle("task").setProject(project);
        Attachment attachment = new Attachment().setId(1L).setTitle("Attachment").setTask(task);
        AttachmentDTO attachmentDTO = new AttachmentDTO().setId(1L).setTitle("Attachment").setTaskId(1L);

        when(modelMapper.map(attachment, AttachmentDTO.class)).thenReturn(attachmentDTO);
        when(attachmentRepository.getById(1L, 1L, 1L)).thenReturn(Optional.of(attachment));

        Optional<AttachmentDTO> result = attachmentService.getById(1L, 1L, 1L);
        assertTrue(result.isPresent());
        assertEquals(attachment.getTitle(), result.get().getTitle());
        verify(attachmentRepository).getById(1L, 1L, 1L);
    }

    @Test
    void testUpdate() {
        Project project = new Project().setId(1L).setTitle("project");
        Task task = new Task().setId(2L).setTitle("title2").setProject(project);
        Attachment attachment = new Attachment().setId(1L).setTitle("title").setTask(task);
        AttachmentDTO attachmentDTO = new AttachmentDTO().setId(1L).setTitle("title").setTaskId(2L);

        when(modelMapper.map(attachment, AttachmentDTO.class)).thenReturn(attachmentDTO);
        when(modelMapper.map(attachmentDTO, Attachment.class)).thenReturn(attachment);
        when(attachmentRepository.update(1L, 2L, 1L, attachment)).thenReturn(Optional.of(attachment));

        Optional<AttachmentDTO> result = attachmentService.update(1L, 2L, 1L, attachmentDTO);
        assertTrue(result.isPresent());
        assertEquals(attachment.getTitle(), result.get().getTitle());
        verify(attachmentRepository).update(1L, 2L, 1L, attachment);
    }

    @Test
    void testDelete() {
        attachmentService.delete(1L, 1L, 1L);
        verify(attachmentRepository).delete(1L, 1L, 1L);
    }
}
