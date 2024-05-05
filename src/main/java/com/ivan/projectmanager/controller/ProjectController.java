package com.ivan.projectmanager.controller;

import com.ivan.projectmanager.dto.ProjectDTO;
import com.ivan.projectmanager.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/projects")
@Validated
public class ProjectController {
    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    @GetMapping()
    public ResponseEntity<List<ProjectDTO>> getAll() {
        List<ProjectDTO> projects = projectService.getAll();
        return ResponseEntity.ok().body(projects);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping()
    public ResponseEntity<ProjectDTO> save(@RequestBody ProjectDTO projectDTO) {
        ProjectDTO savedProject = projectService.save(projectDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProject);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> getById(@PathVariable("id") Long id) {
        Optional<ProjectDTO> projectDTOOptional = projectService.getById(id);
        return projectDTOOptional.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ProjectDTO> update(@PathVariable("id") Long id, @RequestBody ProjectDTO projectDTO) {
        Optional<ProjectDTO> updatedProject = projectService.update(id, projectDTO);
        return updatedProject.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        projectService.delete(id);
        return ResponseEntity.noContent().build();
    }
}