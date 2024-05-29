package com.ivan.projectmanager.service;

import com.ivan.projectmanager.dto.ProjectDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ProjectService {
    Page<ProjectDTO> getAll(Integer page, Integer size);

    ProjectDTO save(ProjectDTO projectDTO);

    Optional<ProjectDTO> getById(Long id);

    Optional<ProjectDTO> update(Long id, ProjectDTO updatedProjectDTO);

    void delete(Long id);
}
