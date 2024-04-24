package com.ivan.projectmanager.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivan.projectmanager.dto.ProjectDTO;
import com.ivan.projectmanager.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectService projectService;
    private final ObjectMapper objectMapper;

    @Autowired
    public ProjectController(ProjectService projectService, ObjectMapper objectMapper) {
        this.projectService = projectService;
        this.objectMapper = objectMapper;
    }

    @GetMapping()
    public String getAll() throws JsonProcessingException {
        return objectMapper.writeValueAsString(projectService.getAll());
    }

    @PostMapping("/new")
    public String save(ProjectDTO projectDTO) throws JsonProcessingException {
        return objectMapper.writeValueAsString(projectService.save(projectDTO));
    }

    @GetMapping("/{id}")
    public String getById(Long id) throws JsonProcessingException {
        Optional<ProjectDTO> projectDTOOptional = projectService.getById(id);
        return objectMapper.writeValueAsString(projectDTOOptional.orElse(null));
    }

    @PatchMapping("/{id}")
    public String update(Long id, ProjectDTO projectDTO) throws JsonProcessingException {
        return objectMapper.writeValueAsString(projectService.update(id, projectDTO));
    }

    @DeleteMapping("/{id}")
    public void delete(Long id) {
        projectService.delete(id);
    }
}
