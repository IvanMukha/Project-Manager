package com.ivan.projectmanager.service;

import com.ivan.projectmanager.dto.TaskCountDTO;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
        Task task = new Task().setTitle("Task 1").setProject(project).setCategory("category");
        Task task2 = new Task().setTitle("Task 2").setProject(project);
        TaskDTO taskDTO = new TaskDTO().setTitle("Task 1").setProjectId(1L);
        TaskDTO taskDTO2 = new TaskDTO().setTitle("Task 2").setProjectId(1L);
        Page<Task> taskPage = new PageImpl<>(List.of(task, task2));
        LocalDateTime startDate = LocalDateTime.parse("2024-04-17 10:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime dueDate = LocalDateTime.parse("2024-04-20 17:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        when(modelMapper.map(task, TaskDTO.class)).thenReturn(taskDTO);
        when(modelMapper.map(task2, TaskDTO.class)).thenReturn(taskDTO2);
        when(taskRepository.getAll("status", "priority", 1L, 1L,
                "category", "label", startDate, startDate,
                dueDate, dueDate, 1L, PageRequest.of(0, 10))).thenReturn(taskPage);

        Page<TaskDTO> resultPage = taskService.getAll("status", "priority", 1L, 1L,
                "category", "label", "2024-04-17 10:00:00", "2024-04-17 10:00:00",
                "2024-04-20 17:00:00", "2024-04-20 17:00:00", 1L, 0, 10);
        List<TaskDTO> result = resultPage.getContent();
        assertEquals(2, result.size());
        assertEquals(task.getTitle(), result.get(0).getTitle());
        assertEquals(task2.getTitle(), result.get(1).getTitle());
        verify(taskRepository).getAll("status", "priority", 1L, 1L,
                "category", "label", startDate, startDate,
                dueDate, dueDate, 1L, PageRequest.of(0, 10));
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

    @Test
    void countTasksByStatusAndDateRange() {
        LocalDateTime dateFrom = LocalDateTime.now();
        LocalDateTime dateTo = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateFromStr = dateFrom.format(formatter);
        String dateToStr = dateTo.format(formatter);
        TaskCountDTO taskCountDTO = new TaskCountDTO(LocalDate.now(), 2L);
        List<TaskCountDTO> taskCountDTOList = new ArrayList<>();
        taskCountDTOList.add(taskCountDTO);
        LocalDateTime parsedDateFrom = LocalDateTime.parse(dateFromStr, formatter);
        LocalDateTime parsedDateTo = LocalDateTime.parse(dateToStr, formatter);

        when(taskRepository.countTasksByStatusAndDateRange("Completed", parsedDateFrom, parsedDateTo, 1L)).thenReturn(taskCountDTOList);

        List<TaskCountDTO> result = taskService.countTasksByStatusAndDateRange("Completed", dateFromStr, dateToStr, 1L);
        assertEquals(1, result.size());
        assertEquals(2L, result.get(0).getCount());
    }

    @Test
    void countTasksByStatusAndDateRangeForUser() {
        LocalDateTime dateFrom = LocalDateTime.now();
        LocalDateTime dateTo = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateFromStr = dateFrom.format(formatter);
        String dateToStr = dateTo.format(formatter);
        TaskCountDTO taskCountDTO = new TaskCountDTO(LocalDate.now(), 2L);
        List<TaskCountDTO> taskCountDTOList = new ArrayList<>();
        taskCountDTOList.add(taskCountDTO);
        LocalDateTime parsedDateFrom = LocalDateTime.parse(dateFromStr, formatter);
        LocalDateTime parsedDateTo = LocalDateTime.parse(dateToStr, formatter);

        when(taskRepository.countTasksByStatusAndDateRangeForUser("Completed", parsedDateFrom, parsedDateTo, 1L, 1L))
                .thenReturn(taskCountDTOList);

        List<TaskCountDTO> result = taskService.countTasksByStatusAndDateRangeForUser("Completed", dateFromStr, dateToStr, 1L, 1L);

        assertEquals(1, result.size());
        assertEquals(2L, result.get(0).getCount());
    }
}
