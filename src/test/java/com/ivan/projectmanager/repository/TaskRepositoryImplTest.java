package com.ivan.projectmanager.repository;

import com.ivan.projectmanager.model.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestRepositoryConfiguration.class)
@WebAppConfiguration
public class TaskRepositoryImplTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    @Sql("classpath:data/taskrepositorytests/insert-tasks.sql")
    public void testGetAll() {
        List<Task> tasks = taskRepository.getAll();
        assertThat(tasks).isNotEmpty();
    }

    @Test
    @Sql("classpath:data/taskrepositorytests/insert-tasks.sql")
    public void testGetById() {
        Optional<Task> task = taskRepository.getById(1L);
        assertTrue(task.isPresent());
        assertEquals("Task 1", task.get().getTitle());
    }

    @Test
    @Sql("classpath:data/taskrepositorytests/insert-tasks.sql")
    public void testDeleteTask() {
        Optional<Task> task = taskRepository.getById(1L);
        assertTrue(task.isPresent());
        taskRepository.delete(1L);
        assertFalse(taskRepository.getById(1L).isPresent());
    }

    @Test
    @Sql("classpath:data/taskrepositorytests/insert-tasks.sql")
    public void testUpdate() {
        Optional<Task> task = taskRepository.getById(1L);
        assertTrue(task.isPresent());
        Task updatedTask = task.get();
        updatedTask.setTitle("Updated Task");
        taskRepository.update(1L, updatedTask);
        Optional<Task> updatedTaskOptional = taskRepository.getById(1L);
        assertTrue(updatedTaskOptional.isPresent());
        assertEquals("Updated Task", updatedTaskOptional.get().getTitle());
    }

    @Test
    @Sql("classpath:data/taskrepositorytests/insert-tasks.sql")
    public void testGetByStatusCriteria() {
        List<Task> foundTasks = taskRepository.getByStatusCriteria("In progress");
        assertThat(foundTasks).isNotEmpty();
        assertThat(foundTasks.getFirst().getStatus()).isEqualTo("In progress");
    }

    @Test
    @Sql("classpath:data/taskrepositorytests/insert-tasks.sql")
    public void testGetByCategoryJpql() {
        List<Task> foundTasks = taskRepository.getByCategoryJpql("Development");
        assertThat(foundTasks).isNotEmpty();
        assertThat(foundTasks).hasSize(1);
        assertThat(foundTasks.getFirst().getCategory()).isEqualTo("Development");
    }

    @Test
    @Sql("classpath:data/taskrepositorytests/insert-tasks.sql")
    public void testGetAllJpqlFetch() {
        List<Task> foundTasks = taskRepository.getAllJpqlFetch();
        assertThat(foundTasks).isNotEmpty();
        assertThat(foundTasks.getFirst().getReporter()).isNotNull();
        assertThat(foundTasks.getFirst().getAssignee()).isNotNull();
        assertThat(foundTasks.getFirst().getProject()).isNotNull();
    }

    @Test
    @Sql("classpath:data/taskrepositorytests/insert-tasks.sql")
    public void testGetAllCriteriaFetch() {
        List<Task> foundTasks = taskRepository.getAllCriteriaFetch();
        assertThat(foundTasks).isNotEmpty();
        assertThat(foundTasks.getFirst().getReporter()).isNotNull();
        assertThat(foundTasks.getFirst().getAssignee()).isNotNull();
        assertThat(foundTasks.getFirst().getProject()).isNotNull();
    }

    @Test
    @Sql("classpath:data/taskrepositorytests/insert-tasks.sql")
    public void testGetAllEntityGraph() {
        List<Task> foundTasks = taskRepository.getAllEntityGraph();
        assertThat(foundTasks).isNotEmpty();
        assertThat(foundTasks.getFirst().getReporter()).isNotNull();
        assertThat(foundTasks.getFirst().getAssignee()).isNotNull();
        assertThat(foundTasks.getFirst().getProject()).isNotNull();
    }
}
