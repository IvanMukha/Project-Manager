package com.ivan.projectmanager.service.impl;

import com.ivan.projectmanager.dto.ProjectDTO;
import com.ivan.projectmanager.model.Project;
import com.ivan.projectmanager.repository.ProjectRepository;
import com.ivan.projectmanager.service.ProjectService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ModelMapper modelMapper;
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectServiceImpl(ModelMapper modelMapper, ProjectRepository projectRepository) {
        this.modelMapper = modelMapper;
        this.projectRepository = projectRepository;
    }

    public List<ProjectDTO> getAll() {
        return projectRepository.getAll().stream().map(this::mapProjectToDTO).collect(Collectors.toList());
    }

    @Transactional
    public ProjectDTO save(ProjectDTO projectDTO) {
        return mapProjectToDTO(projectRepository.save(mapDTOToProject(projectDTO)));
    }

    public Optional<ProjectDTO> getById(Long id) {
        Optional<Project> projectOptional = projectRepository.getById(id);
        return projectOptional.map(this::mapProjectToDTO);
    }

    @Transactional
    public Optional<ProjectDTO> update(Long id, ProjectDTO updatedProjectDTO) {
        Optional<Project> projectOptional = projectRepository.update(id, mapDTOToProject(updatedProjectDTO));
        return projectOptional.map(this::mapProjectToDTO);
    }

    @Transactional
    public void delete(Long id) {
        projectRepository.delete(id);
    }

    private Project mapDTOToProject(ProjectDTO projectDTO) {
        return modelMapper.map(projectDTO, Project.class);
    }

    private ProjectDTO mapProjectToDTO(Project project) {
        return modelMapper.map(project, ProjectDTO.class);
    }
}

