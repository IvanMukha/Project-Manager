package com.ivan.projectmanager.repository;

import com.ivan.projectmanager.config.ApplicationConfig;
import com.ivan.projectmanager.model.Project;
import com.ivan.projectmanager.repository.impl.AttachmentRepositoryImpl;
import com.ivan.projectmanager.repository.impl.ProjectRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
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
@ContextConfiguration(classes = {ApplicationConfig.class, ProjectRepositoryImpl.class, TestRepositoryConfiguration.class})
public class ProjectRepositoryImplTest {

    @Autowired
    private ProjectRepositoryImpl projectRepository;

    @Test
    @Sql("classpath:data/projectrepositorytests/insert-projects.sql")
    public void testGetAll() {
        List<Project> projects = projectRepository.getAll();
        assertThat(projects).isNotEmpty();
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
    public void testFindByStatusCriteria() {
        List<Project> foundProjects = projectRepository.findByStatusCriteria("Active");
        assertThat(foundProjects).isNotEmpty();
        assertThat(foundProjects).hasSize(1);
        assertThat(foundProjects.getFirst().getStatus()).isEqualTo("Active");
    }

    @Test
    @Sql("classpath:data/projectrepositorytests/insert-projects.sql")
    public void testFindByTitleJpql() {
        List<Project> foundProjects = projectRepository.findByTitleJpql("Project ABC");
        assertThat(foundProjects).isNotEmpty();
        assertThat(foundProjects).hasSize(1);
        assertThat(foundProjects.getFirst().getTitle()).isEqualTo("Project ABC");
    }

    @Test
    @Sql("classpath:data/projectrepositorytests/insert-projects.sql")
    public void testFindAllCriteriaFetch() {
        List<Project> foundProjects = projectRepository.findAllCriteriaFetch();
        assertThat(foundProjects).isNotEmpty();
        assertThat(foundProjects.getFirst().getTeam()).isNotNull();
    }

    @Test
    @Sql("classpath:data/projectrepositorytests/insert-projects.sql")
    public void testFindAllJpqlFetch() {
        List<Project> foundProjects = projectRepository.findAllJpqlFetch();
        assertThat(foundProjects).isNotEmpty();
        assertThat(foundProjects.getFirst().getTeam()).isNotNull();
    }

    @Test
    @Sql("classpath:data/projectrepositorytests/insert-projects.sql")
    public void testFindAllEntityGraphFetch() {
        List<Project> foundProjects = projectRepository.findAllEntityGraphFetch();
        assertThat(foundProjects).isNotEmpty();
        assertThat(foundProjects.getFirst().getTeam()).isNotNull();
    }
}
