package com.ivan.projectManager.repository;

import com.ivan.projectManager.model.Project;
import com.ivan.projectManager.model.Task;
import com.ivan.projectManager.model.Team;
import com.ivan.projectManager.model.User;

public interface EntityCreation {
    Task createTaskWithRelatedEntities(Task task, User user, Team team, Project project);
}
