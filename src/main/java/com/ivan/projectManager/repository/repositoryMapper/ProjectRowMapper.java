package com.ivan.projectManager.repository.repositoryMapper;

import com.ivan.projectManager.model.Project;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ProjectRowMapper implements RowMapper<Project> {
    public List<Project> mapAll(ResultSet resultSet) {
        List<Project> projects = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Project project = map(resultSet);
                projects.add(project);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to map projects", e);
        }
        return projects;
    }

    public Project map(ResultSet resultSet) {
        try {
            Project project = new Project();
            project.setId(resultSet.getInt("id"));
            project.setTitle(resultSet.getString("title"));
            project.setDescription(resultSet.getString("description"));
            project.setStartDate(resultSet.getObject("start_date", LocalDateTime.class));
            project.setStatus(resultSet.getString("status"));
            project.setTeamId(resultSet.getInt("team_id"));
            project.setManagerId(resultSet.getInt("manager_id"));
            return project;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to map project", e);
        }
    }
}
