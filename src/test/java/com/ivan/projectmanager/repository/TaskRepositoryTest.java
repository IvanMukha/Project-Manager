package com.ivan.projectmanager.repository;

import com.ivan.projectmanager.model.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestRepositoryConfiguration.class})
@TestPropertySource("classpath:application-test.properties")
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    @Sql("classpath:data/taskrepositorytests/insert-tasks.sql")
    public void testGetAll() {
        LocalDateTime startDate = LocalDateTime.parse("2024-04-17 10:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime dueDate = LocalDateTime.parse("2024-04-20 17:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Page<Task> tasks = taskRepository.getAll("In progress", "High",
                1L, 2L, "Development", "Bug", startDate, startDate,
                dueDate, dueDate, 1L, PageRequest.of(0, 10));
        assertThat(tasks).isNotEmpty();
    }

    @Test
    @Sql("classpath:data/taskrepositorytests/insert-tasks.sql")
    public void testGetById() {
        Optional<Task> task = taskRepository.getById(1L, 1L);
        assertTrue(task.isPresent());
        assertEquals("Task 1", task.get().getTitle());
    }

    @Test
    @Sql("classpath:data/taskrepositorytests/insert-tasks.sql")
    public void testDeleteTask() {
        Optional<Task> task = taskRepository.getById(1L, 1L);
        assertTrue(task.isPresent());
        taskRepository.delete(1L, 1L);
        assertFalse(taskRepository.getById(1L, 1L).isPresent());
    }

    @Test
    @Sql("classpath:data/taskrepositorytests/insert-tasks.sql")
    public void testUpdate() {
        Optional<Task> task = taskRepository.getById(1L, 1L);
        assertTrue(task.isPresent());
        Task updatedTask = task.get();
        updatedTask.setTitle("Updated Task");
        taskRepository.update(1L, 1L, updatedTask);
        Optional<Task> updatedTaskOptional = taskRepository.getById(1L, 1L);
        assertTrue(updatedTaskOptional.isPresent());
        assertEquals("Updated Task", updatedTaskOptional.get().getTitle());
    }

    @Test
    @Sql("classpath:data/taskrepositorytests/insert-tasks.sql")
    public void testGetByStatus() {
        List<Task> foundTasks = taskRepository.getByStatus("In progress");
        assertThat(foundTasks).isNotEmpty();
        assertThat(foundTasks.getFirst().getStatus()).isEqualTo("In progress");
    }

    @Test
    @Sql("classpath:data/taskrepositorytests/insert-tasks.sql")
    public void testGetByCategory() {
        List<Task> foundTasks = taskRepository.getByCategory("Development");
        assertThat(foundTasks).isNotEmpty();
        assertThat(foundTasks).hasSize(1);
        assertThat(foundTasks.getFirst().getCategory()).isEqualTo("Development");
    }

}
