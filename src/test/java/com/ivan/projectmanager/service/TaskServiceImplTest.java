package com.ivan.projectmanager.service;

import com.ivan.projectmanager.dto.TaskDTO;
import com.ivan.projectmanager.model.Task;
import com.ivan.projectmanager.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;

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
public class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void testGetAllTasks() {
        Task task = new Task().setTitle("Task 1");
        Task task2 = new Task().setTitle("Task 2");
        when(taskRepository.getAll()).thenReturn(List.of(task, task2));
        List<TaskDTO> result = taskService.getAll();
        assertEquals(2, result.size());
        assertEquals(task.getTitle(), result.get(0).getTitle());
        assertEquals(task2.getTitle(), result.get(1).getTitle());
        verify(taskRepository).getAll();
    }

    @Test
    void testSaveTask() {
        Task task = new Task().setTitle("Test Task");
        TaskDTO taskDTO = new TaskDTO().setTitle("Test Task");
        Mockito.when(taskRepository.save(task)).thenReturn(task);
        TaskDTO savedTaskDTO = taskService.save(taskDTO);
        assertNotNull(savedTaskDTO);
        assertEquals(task.getTitle(), savedTaskDTO.getTitle());
        verify(taskRepository).save(any());
    }

    @Test
    void testGetTaskById() {
        String title = "Test Task";
        Task task = new Task().setTitle(title);
        when(taskRepository.getById(1L)).thenReturn(Optional.of(task));
        Optional<TaskDTO> result = taskService.getById(1L);
        assertTrue(result.isPresent());
        assertEquals(title, result.get().getTitle());
        verify(taskRepository).getById(1L);
    }

    @Test
    void testDeleteTask() {
        Long id = 1L;
        taskService.delete(id);
        verify(taskRepository).delete(id);
    }
}
