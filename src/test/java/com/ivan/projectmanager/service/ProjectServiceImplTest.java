package com.ivan.projectmanager.service;

import com.ivan.projectmanager.dto.ProjectDTO;
import com.ivan.projectmanager.model.Project;
import com.ivan.projectmanager.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
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
public class ProjectServiceImplTest {
    @Mock
    private ProjectRepository projectRepository;
    @InjectMocks
    private ProjectService projectService;

    @Test
    void getAllProjects() {
        Project p1 = new Project();
        p1.setTitle("title1");
        Project p2 = new Project();
        p2.setTitle("title2");
        when(projectRepository.getAll()).thenReturn(List.of(p1, p2));
        List<ProjectDTO> result = projectService.getAll();
        assertEquals(2, result.size());
        assertEquals(p1.getTitle(), result.get(0).getTitle());
        assertEquals(p2.getTitle(), result.get(1).getTitle());
        verify(projectRepository.getAll());
    }

    @Test
    public void testSave() {
        Project project = new Project();
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setTitle("title");
        project.setTitle("title");
        Mockito.when(projectRepository.save(project)).thenReturn(project);
        ProjectDTO projectDTO2 = projectService.save(projectDTO);
        assertNotNull(projectDTO2);
        assertEquals(projectDTO2.getTitle(), project.getTitle());
        verify(projectRepository).save(any());
    }

    @Test
    void testGetById() {
        long id = 1L;
        Project project = new Project();
        project.setTitle("title");
        when(projectRepository.getById(id)).thenReturn(Optional.of(project));
        Optional<ProjectDTO> result = projectService.getById(id);
        assertTrue(result.isPresent());
        assertEquals(project.getTitle(), result.get().getTitle());
        verify(projectRepository.getById(1L));
    }

    @Test
    void testUpdate() {
        Project project = new Project();
        project.setTitle("title");
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setTitle("title");
        when(projectRepository.update(1L, project)).thenReturn(Optional.of(project));
        Optional<ProjectDTO> result = projectService.update(1L, projectDTO);
        assertTrue(result.isPresent());
        assertEquals(project.getTitle(), result.get().getTitle());
        verify(projectRepository).update(1L, project);
    }

    @Test
    void testDelete() {
        Long id = 1L;
        projectService.delete(id);
        verify(projectRepository).delete(id);
    }


}
