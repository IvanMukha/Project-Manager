package com.ivan.projectmanager.service.impl;

import com.ivan.projectmanager.dto.ProjectDTO;
import com.ivan.projectmanager.exeptions.HandleCustomIllegalArgumentException;
import com.ivan.projectmanager.exeptions.HandleCustomNotFoundException;
import com.ivan.projectmanager.exeptions.HandleCustomNullPointerException;
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
        checkProjectDTO(projectDTO);
        return mapProjectToDTO(projectRepository.save(mapDTOToProject(projectDTO)));
    }

    public Optional<ProjectDTO> getById(Long id) {
        checkId(id);
        Optional<Project> projectOptional = projectRepository.getById(id);
        if (projectOptional.isEmpty()) {
            throw new HandleCustomNotFoundException("Project with id " + id + " not found");
        }
        return projectOptional.map(this::mapProjectToDTO);
    }

    @Transactional
    public Optional<ProjectDTO> update(Long id, ProjectDTO updatedProjectDTO) {
        checkId(id);
        checkProjectDTO(updatedProjectDTO);
        Optional<Project> projectOptional = projectRepository.update(id, mapDTOToProject(updatedProjectDTO));
        if (projectOptional.isEmpty()) {
            throw new HandleCustomNotFoundException("Project with id " + id + " not found");
        }
        return projectOptional.map(this::mapProjectToDTO);
    }

    @Transactional
    public void delete(Long id) {
        checkId(id);
        projectRepository.delete(id);
    }

    private void checkProjectDTO(ProjectDTO projectDTO) {
        if (projectDTO == null) {
            throw new HandleCustomNullPointerException("ProjectDTO is null");
        }
        if (projectDTO.getTitle() == null) {
            throw new HandleCustomNullPointerException("Project title cannot be null");
        }
        if (projectDTO.getTitle().isEmpty()) {
            throw new HandleCustomIllegalArgumentException("Project title cannot be empty");
        }
    }

    private void checkId(Long id) {
        if (id == null) {
            throw new HandleCustomNullPointerException("Project id cannot be null");
        }
        if (id <= 0) {
            throw new HandleCustomIllegalArgumentException("Project id must be greater than 0");
        }
    }

    private Project mapDTOToProject(ProjectDTO projectDTO) {
        return modelMapper.map(projectDTO, Project.class);
    }

    private ProjectDTO mapProjectToDTO(Project project) {
        return modelMapper.map(project, ProjectDTO.class);
    }
}
