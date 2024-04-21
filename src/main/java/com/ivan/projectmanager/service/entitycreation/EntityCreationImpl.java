package com.ivan.projectmanager.service.entitycreation;

import com.ivan.projectmanager.dto.ProjectDTO;
import com.ivan.projectmanager.dto.TaskDTO;
import com.ivan.projectmanager.dto.TeamDTO;
import com.ivan.projectmanager.dto.UserDTO;
import com.ivan.projectmanager.model.Project;
import com.ivan.projectmanager.model.Task;
import com.ivan.projectmanager.model.Team;
import com.ivan.projectmanager.model.User;
import com.ivan.projectmanager.repository.ProjectRepository;
import com.ivan.projectmanager.repository.TaskRepository;
import com.ivan.projectmanager.repository.TeamRepository;
import com.ivan.projectmanager.repository.UserRepository;
import com.ivan.projectmanager.service.EntityCreationService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EntityCreationImpl implements EntityCreationService {
    private final ModelMapper modelMapper;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final ProjectRepository projectRepository;

    public EntityCreationImpl(ModelMapper modelMapper, TaskRepository taskRepository, UserRepository userRepository, TeamRepository teamRepository, ProjectRepository projectRepository) {
        this.modelMapper = modelMapper;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
        this.projectRepository = projectRepository;
    }

    @Transactional
    public TaskDTO createTaskWithRelatedEntities(TaskDTO taskDTO, UserDTO userDTO, TeamDTO teamDTO, ProjectDTO projectDTO) {
        userRepository.save(mapDTOToUser(userDTO));
        teamRepository.save(mapDTOToTeam(teamDTO));
        projectRepository.save(mapDTOToProject(projectDTO));
        return mapTaskToDTO(taskRepository.save(mapDTOToTask(taskDTO)));
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