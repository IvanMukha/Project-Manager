package com.ivan.projectmanager.service;

import com.ivan.projectmanager.dto.TaskDTO;
import com.ivan.projectmanager.model.Project;
import com.ivan.projectmanager.model.Task;
import com.ivan.projectmanager.model.User;
import com.ivan.projectmanager.repository.ProjectRepository;
import com.ivan.projectmanager.repository.TaskRepository;
import com.ivan.projectmanager.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {

    @Mock
    ModelMapper modelMapper;
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private ProjectRepository projectRepository;
    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    void testGetAllTasks() {
        Project project = new Project().setId(1L).setTitle("project");
        Task task = new Task().setTitle("Task 1").setProject(project);
        Task task2 = new Task().setTitle("Task 2").setProject(project);
        TaskDTO taskDTO = new TaskDTO().setTitle("Task 1").setProjectId(1L);
        TaskDTO taskDTO2 = new TaskDTO().setTitle("Task 2").setProjectId(1L);
        when(modelMapper.map(task, TaskDTO.class)).thenReturn(taskDTO);
        when(modelMapper.map(task2, TaskDTO.class)).thenReturn(taskDTO2);
        when(taskRepository.getAll(1L)).thenReturn(List.of(task, task2));
        List<TaskDTO> result = taskService.getAll(1L);
        assertEquals(2, result.size());
        assertEquals(task.getTitle(), result.get(0).getTitle());
        assertEquals(task2.getTitle(), result.get(1).getTitle());
        verify(taskRepository).getAll(1L);
    }

    @Test
    void testSaveTask() {
        User user = new User().setId(1L);
        Project project = new Project().setId(1L);
        Task task = new Task().setTitle("Test Task").setReporter(user).setProject(project);
        TaskDTO taskDTO = new TaskDTO().setTitle("Test Task").setReporterId(1L).setProjectId(1L);
        when(modelMapper.map(task, TaskDTO.class)).thenReturn(taskDTO);
        when(modelMapper.map(taskDTO, Task.class)).thenReturn(task);
        when(projectRepository.getById(1L)).thenReturn(Optional.ofNullable(project));
        when(taskRepository.save(task)).thenReturn(task);
        TaskDTO savedTaskDTO = taskService.save(1L, taskDTO);
        assertNotNull(savedTaskDTO);
        assertEquals(task.getTitle(), savedTaskDTO.getTitle());
        verify(taskRepository).save(any());
    }

    @Test
    void testGetTaskById() {
        Task task = new Task().setTitle("Test Task");
        TaskDTO taskDTO = new TaskDTO().setTitle("Test Task");
        when(modelMapper.map(task, TaskDTO.class)).thenReturn(taskDTO);
        when(taskRepository.getById(1L, 1L)).thenReturn(Optional.of(task));
        Optional<TaskDTO> result = taskService.getById(1L, 1L);
        assertTrue(result.isPresent());
        assertEquals("Test Task", result.get().getTitle());
        verify(taskRepository).getById(1L, 1L);
    }

    @Test
    void testDeleteTask() {
        taskService.delete(1L, 1L);
        verify(taskRepository).delete(1L, 1L);
    }
}
