package com.ivan.projectManager.repository.entityCreationRepository;

import com.ivan.projectManager.model.Project;
import com.ivan.projectManager.model.Task;
import com.ivan.projectManager.model.Team;
import com.ivan.projectManager.model.User;
import com.ivan.projectManager.repository.EntityCreation;
import com.ivan.projectManager.repository.ProjectRepository;
import com.ivan.projectManager.repository.TaskRepository;
import com.ivan.projectManager.repository.TeamRepository;
import com.ivan.projectManager.repository.UserRepository;
import org.springframework.stereotype.Repository;

@Repository
public class EntityCreationRepository implements EntityCreation {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final ProjectRepository projectRepository;

    public EntityCreationRepository(TaskRepository taskRepository, UserRepository userRepository, TeamRepository teamRepository, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
        this.projectRepository = projectRepository;
    }

    public Task createTaskWithRelatedEntities(Task task, User user, Team team, Project project) {
        userRepository.save(user);
        teamRepository.save(team);
        projectRepository.save(project);
        return taskRepository.save(task);
    }

}
