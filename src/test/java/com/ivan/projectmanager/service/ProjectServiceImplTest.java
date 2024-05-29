package com.ivan.projectmanager.service;

import com.ivan.projectmanager.dto.ProjectDTO;
import com.ivan.projectmanager.model.Project;
import com.ivan.projectmanager.repository.ProjectRepository;
import com.ivan.projectmanager.service.impl.ProjectServiceImpl;
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
public class ProjectServiceImplTest {
    @Mock
    ModelMapper modelMapper;
    @Mock
    private ProjectRepository projectRepository;
    @InjectMocks
    private ProjectServiceImpl projectService;

    @Test
    void getAllProjects() {
        Project project = new Project().setTitle("title1");
        Project project2 = new Project().setTitle("title2");
        ProjectDTO projectDTO = new ProjectDTO().setTitle("title1");
        ProjectDTO projectDTO2 = new ProjectDTO().setTitle("title2");
        List<Project> projects = List.of(project, project2);
        Page<Project> page = new PageImpl<>(projects, PageRequest.of(0, 10), projects.size());

        when(modelMapper.map(project, ProjectDTO.class)).thenReturn(projectDTO);
        when(modelMapper.map(project2, ProjectDTO.class)).thenReturn(projectDTO2);
        when(projectRepository.getAll(PageRequest.of(0, 10))).thenReturn(page);

        List<ProjectDTO> result = projectService.getAll(0, 10).getContent();
        assertEquals(2, result.size());
        assertEquals(project.getTitle(), result.get(0).getTitle());
        assertEquals(project2.getTitle(), result.get(1).getTitle());
        verify(projectRepository).getAll(PageRequest.of(0, 10));
    }

    @Test
    public void testSave() {
        Project project = new Project().setTitle("title");
        ProjectDTO projectDTO = new ProjectDTO().setTitle("title");
        when(modelMapper.map(project, ProjectDTO.class)).thenReturn(projectDTO);
        when(modelMapper.map(projectDTO, Project.class)).thenReturn(project);
        Mockito.when(projectRepository.save(project)).thenReturn(project);
        ProjectDTO projectDTO2 = projectService.save(projectDTO);
        assertNotNull(projectDTO2);
        assertEquals(projectDTO2.getTitle(), project.getTitle());
        verify(projectRepository).save(any());
    }

    @Test
    void testGetById() {
        long id = 1L;
        Project project = new Project().setTitle("title");
        ProjectDTO projectDTO = new ProjectDTO().setTitle("title");
        when(modelMapper.map(project, ProjectDTO.class)).thenReturn(projectDTO);
        when(projectRepository.getById(id)).thenReturn(Optional.of(project));
        Optional<ProjectDTO> result = projectService.getById(id);
        assertTrue(result.isPresent());
        assertEquals(project.getTitle(), result.get().getTitle());
        verify(projectRepository).getById(1L);
    }

    @Test
    void testUpdate() {
        Project project = new Project().setTitle("title");
        ProjectDTO projectDTO = new ProjectDTO().setTitle("title");
        when(modelMapper.map(project, ProjectDTO.class)).thenReturn(projectDTO);
        when(modelMapper.map(projectDTO, Project.class)).thenReturn(project);
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
