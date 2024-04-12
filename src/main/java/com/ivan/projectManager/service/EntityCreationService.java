package com.ivan.projectManager.service;

import com.ivan.projectManager.dto.ProjectDTO;
import com.ivan.projectManager.dto.TaskDTO;
import com.ivan.projectManager.dto.TeamDTO;
import com.ivan.projectManager.dto.UserDTO;

public interface EntityCreationService {
    TaskDTO createTaskWithRelatedEntities(TaskDTO taskDTO, UserDTO userDTO, TeamDTO teamDTO, ProjectDTO projectDTO);
}
