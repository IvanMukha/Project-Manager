package org.example.application.service.impl;

import org.example.application.repository.ProjectRepository;
import org.example.application.service.ProjectService;
import org.example.application.dto.ProjectDTO;
import org.example.application.model.Project;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ModelMapper modelMapper;
    private final ProjectRepository projectRepositoryImpl;

    @Autowired
    public ProjectServiceImpl(ModelMapper modelMapper, ProjectRepository projectRepositoryImpl) {
        this.modelMapper = modelMapper;
        this.projectRepositoryImpl = projectRepositoryImpl;
    }

    public List<ProjectDTO> getAll() {
        return projectRepositoryImpl.getAll().stream().map(this::mapProjectToDTO).collect(Collectors.toList());
    }

    public ProjectDTO save(ProjectDTO projectDTO) {
        return mapProjectToDTO(projectRepositoryImpl.save(mapDTOToProject(projectDTO)));
    }

    public Optional<ProjectDTO> getById(int id) {
        Optional<Project> projectOptional = projectRepositoryImpl.getById(id);
        return projectOptional.map(this::mapProjectToDTO);
    }


    public Optional<ProjectDTO> update(int id, ProjectDTO updatedProjectDTO) {
        Optional<Project> projectOptional = projectRepositoryImpl.update(id, mapDTOToProject(updatedProjectDTO));
        return projectOptional.map(this::mapProjectToDTO);
    }

    public void delete(int id) {
        projectRepositoryImpl.delete(id);
    }

    private Project mapDTOToProject(ProjectDTO projectDTO) {
        return modelMapper.map(projectDTO, Project.class);
    }

    private ProjectDTO mapProjectToDTO(Project project) {
        return modelMapper.map(project, ProjectDTO.class);
    }
}


