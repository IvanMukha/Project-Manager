package com.ivan.projectmanager.repository;

import com.ivan.projectmanager.model.Project;
import com.ivan.projectmanager.model.Task;
import com.ivan.projectmanager.model.Team;
import com.ivan.projectmanager.model.User;

public interface EntityCreation {
    Task createTaskWithRelatedEntities(Task task, User user, Team team, Project project);
}
