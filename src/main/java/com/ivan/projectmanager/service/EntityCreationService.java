package com.ivan.projectmanager.service;

import com.ivan.projectmanager.dto.ProjectDTO;
import com.ivan.projectmanager.dto.TaskDTO;
import com.ivan.projectmanager.dto.TeamDTO;
import com.ivan.projectmanager.dto.UserDTO;

public interface EntityCreationService {
    TaskDTO createTaskWithRelatedEntities(TaskDTO taskDTO, UserDTO userDTO, TeamDTO teamDTO, ProjectDTO projectDTO);
}