package com.ivan.projectmanager.repository;

import com.ivan.projectmanager.model.Project;
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

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestRepositoryConfiguration.class})
@TestPropertySource("classpath:application-test.properties")
public class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    @Sql("classpath:data/projectrepositorytests/insert-projects.sql")
    public void testGetAll() {
        Page<Project> projectPage = projectRepository.getAll(PageRequest.of(0, 10));

        assertThat(projectPage).isNotEmpty();
    }

    @Test
    @Sql("classpath:data/projectrepositorytests/insert-projects.sql")
    public void testGetById() {
        Optional<Project> project = projectRepository.getById(1L);

        assertTrue(project.isPresent());
        assertEquals("Project ABC", project.get().getTitle());
    }

    @Test
    @Sql("classpath:data/projectrepositorytests/insert-projects.sql")
    public void testDeleteProject() {
        Optional<Project> project = projectRepository.getById(1L);
        assertTrue(project.isPresent());

        projectRepository.delete(1L);
        assertFalse(projectRepository.getById(1L).isPresent());
    }

    @Test
    @Sql("classpath:data/projectrepositorytests/insert-projects.sql")
    public void testUpdate() {
        Optional<Project> project = projectRepository.getById(1L);
        assertTrue(project.isPresent());

        Project updatedProject = project.get();
        updatedProject.setTitle("Updated Project");
        projectRepository.update(1L, updatedProject);

        Optional<Project> updatedProjectOptional = projectRepository.getById(1L);
        assertTrue(updatedProjectOptional.isPresent());
        assertEquals("Updated Project", updatedProjectOptional.get().getTitle());
    }

    @Test
    @Sql("classpath:data/projectrepositorytests/insert-projects.sql")
    public void testFindByStatus() {
        List<Project> foundProjects = projectRepository.findByStatus("Active");

        assertThat(foundProjects).isNotEmpty();
        assertThat(foundProjects).hasSize(1);
        assertThat(foundProjects.getFirst().getStatus()).isEqualTo("Active");
    }

    @Test
    @Sql("classpath:data/projectrepositorytests/insert-projects.sql")
    public void testFindByTitle() {
        List<Project> foundProjects = projectRepository.findByTitle("Project ABC");

        assertThat(foundProjects).isNotEmpty();
        assertThat(foundProjects).hasSize(1);
        assertThat(foundProjects.getFirst().getTitle()).isEqualTo("Project ABC");
    }
}
