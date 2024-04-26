package com.ivan.projectmanager.service;

import com.ivan.projectmanager.dto.TaskDTO;
import com.ivan.projectmanager.model.Project;
import com.ivan.projectmanager.model.Task;
import com.ivan.projectmanager.model.User;
import com.ivan.projectmanager.repository.TaskRepository;
import com.ivan.projectmanager.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = TestServiceConfiguration.class)
@WebAppConfiguration
public class TaskServiceImplTest {

    @Mock
    ModelMapper modelMapper;
    @Mock
    private TaskRepository taskRepository;
    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    void testGetAllTasks() {
        Task task = new Task().setTitle("Task 1");
        Task task2 = new Task().setTitle("Task 2");
        TaskDTO taskDTO = new TaskDTO().setTitle("Task 1");
        TaskDTO taskDTO2 = new TaskDTO().setTitle("Task 2");
        when(modelMapper.map(task, TaskDTO.class)).thenReturn(taskDTO);
        when(modelMapper.map(task2, TaskDTO.class)).thenReturn(taskDTO2);
        when(taskRepository.getAll()).thenReturn(List.of(task, task2));
        List<TaskDTO> result = taskService.getAll();
        assertEquals(2, result.size());
        assertEquals(task.getTitle(), result.get(0).getTitle());
        assertEquals(task2.getTitle(), result.get(1).getTitle());
        verify(taskRepository).getAll();
    }

    @Test
    void testSaveTask() {
        User user = new User().setId(1L);
        Project project = new Project().setId(1L);
        Task task = new Task().setTitle("Test Task").setReporter(user).setProject(project);
        TaskDTO taskDTO = new TaskDTO().setTitle("Test Task").setReporterId(1L).setProjectId(1L);
        when(modelMapper.map(task, TaskDTO.class)).thenReturn(taskDTO);
        when(modelMapper.map(taskDTO, Task.class)).thenReturn(task);
        Mockito.when(taskRepository.save(task)).thenReturn(task);
        TaskDTO savedTaskDTO = taskService.save(taskDTO);
        assertNotNull(savedTaskDTO);
        assertEquals(task.getTitle(), savedTaskDTO.getTitle());
        verify(taskRepository).save(any());
    }

    @Test
    void testGetTaskById() {
        Task task = new Task().setTitle("Test Task");
        TaskDTO taskDTO = new TaskDTO().setTitle("Test Task");
        when(modelMapper.map(task, TaskDTO.class)).thenReturn(taskDTO);
        when(taskRepository.getById(1L)).thenReturn(Optional.of(task));
        Optional<TaskDTO> result = taskService.getById(1L);
        assertTrue(result.isPresent());
        assertEquals("Test Task", result.get().getTitle());
        verify(taskRepository).getById(1L);
    }

    @Test
    void testDeleteTask() {
        Long id = 1L;
        taskService.delete(id);
        verify(taskRepository).delete(id);
    }
}
