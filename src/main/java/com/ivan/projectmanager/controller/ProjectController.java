package com.ivan.projectmanager.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivan.projectmanager.dto.ProjectDTO;
import com.ivan.projectmanager.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class ProjectController {
    private final ProjectService projectService;
    private final ObjectMapper objectMapper;

    @Autowired
    public ProjectController(ProjectService projectService, ObjectMapper objectMapper) {
        this.projectService = projectService;
        this.objectMapper = objectMapper;
    }

    public String getAll() throws JsonProcessingException {
        return objectMapper.writeValueAsString(projectService.getAll());
    }

    public String save(ProjectDTO projectDTO) throws JsonProcessingException {
        return objectMapper.writeValueAsString(projectService.save(projectDTO));
    }

    public String getById(Long id) throws JsonProcessingException {
        Optional<ProjectDTO> projectDTOOptional = projectService.getById(id);
        return objectMapper.writeValueAsString(projectDTOOptional.orElse(null));
    }

    public String update(Long id, ProjectDTO projectDTO) throws JsonProcessingException {
        return objectMapper.writeValueAsString(projectService.update(id, projectDTO));
    }

    public void delete(Long id) {
        projectService.delete(id);
    }
}
