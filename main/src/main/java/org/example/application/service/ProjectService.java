package org.example.application.service;

import org.example.application.dto.ProjectDTO;
import org.example.application.model.Project;
import org.example.application.repository.ProjectRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectService implements BaseService<Project, ProjectDTO> {
    private final ModelMapper modelMapper;
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ModelMapper modelMapper, ProjectRepository projectRepository) {
        this.modelMapper = modelMapper;
        this.projectRepository = projectRepository;
    }

    public List<ProjectDTO> getAll() {
        return projectRepository.getAll().stream().map(this::mapProjectToDTO).collect(Collectors.toList());
    }

    public ProjectDTO save(ProjectDTO projectDTO) {
        return mapProjectToDTO(projectRepository.save(mapDTOToProject(projectDTO)));
    }

    public Optional<ProjectDTO> getById(int id) {
        Optional<Project> projectOptional = projectRepository.getById(id);
        return projectOptional.map(this::mapProjectToDTO);
    }


    public Optional<ProjectDTO> update(int id, ProjectDTO updatedProjectDTO) {
        Optional<Project> projectOptional = projectRepository.update(id, mapDTOToProject(updatedProjectDTO));
        return projectOptional.map(this::mapProjectToDTO);
    }

    public void delete(int id) {
        projectRepository.delete(id);
    }

    private Project mapDTOToProject(ProjectDTO projectDTO) {
        return modelMapper.map(projectDTO, Project.class);
    }

    private ProjectDTO mapProjectToDTO(Project project) {
        return modelMapper.map(project, ProjectDTO.class);
    }
}


