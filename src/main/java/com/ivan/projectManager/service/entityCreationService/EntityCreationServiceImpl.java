package com.ivan.projectManager.service.entityCreationService;

import com.ivan.projectManager.annotations.Transaction;
import com.ivan.projectManager.dto.ProjectDTO;
import com.ivan.projectManager.dto.TaskDTO;
import com.ivan.projectManager.dto.TeamDTO;
import com.ivan.projectManager.dto.UserDTO;
import com.ivan.projectManager.model.Project;
import com.ivan.projectManager.model.Task;
import com.ivan.projectManager.model.Team;
import com.ivan.projectManager.model.User;
import com.ivan.projectManager.repository.entityCreationRepository.EntityCreationRepository;
import com.ivan.projectManager.service.EntityCreationService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class EntityCreationServiceImpl implements EntityCreationService {
    private final ModelMapper modelMapper;
    private final EntityCreationRepository entityCreationRepository;

    public EntityCreationServiceImpl(ModelMapper modelMapper, EntityCreationRepository entityCreationRepository) {
        this.modelMapper = modelMapper;
        this.entityCreationRepository = entityCreationRepository;
    }

    @Transaction
    public TaskDTO createTaskWithRelatedEntities(TaskDTO taskDTO, UserDTO userDTO, TeamDTO teamDTO, ProjectDTO projectDTO) {
        return mapTaskToDTO(entityCreationRepository.createTaskWithRelatedEntities(mapDTOToTask(taskDTO), mapDTOToUser(userDTO), mapDTOToTeam(teamDTO), mapDTOToProject(projectDTO)));
    }


    private Task mapDTOToTask(TaskDTO taskDTO) {
        return modelMapper.map(taskDTO, Task.class);
    }

    private TaskDTO mapTaskToDTO(Task task) {
        return modelMapper.map(task, TaskDTO.class);
    }

    private User mapDTOToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    private Team mapDTOToTeam(TeamDTO teamDTO) {
        return modelMapper.map(teamDTO, Team.class);
    }

    private Project mapDTOToProject(ProjectDTO projectDTO) {
        return modelMapper.map(projectDTO, Project.class);
    }

}

