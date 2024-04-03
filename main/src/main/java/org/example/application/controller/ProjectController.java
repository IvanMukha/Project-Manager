package org.example.application.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.application.dto.ProjectDTO;
import org.example.application.service.ProjectService;
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

    public void save(ProjectDTO projectDTO) {
        projectService.save(projectDTO);
    }

    public String getById(int id) throws JsonProcessingException {
        Optional<ProjectDTO> projectDTOOptional = projectService.getById(id);
        return objectMapper.writeValueAsString(projectDTOOptional.orElse(null));
    }

    public String getAll() throws JsonProcessingException {
        return objectMapper.writeValueAsString(projectService.getAll());
    }

    public void update(int id, ProjectDTO projectDTO) {
        projectService.update(id, projectDTO);
    }

    public void delete(int id) {
        projectService.delete(id);
    }
}
