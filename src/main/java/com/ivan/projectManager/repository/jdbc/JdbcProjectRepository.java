package com.ivan.projectManager.repository.jdbc;

import com.ivan.projectManager.model.Project;
import com.ivan.projectManager.repository.ProjectRepository;
import com.ivan.projectManager.utils.ConnectionHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class JdbcProjectRepository implements ProjectRepository {

    private final ConnectionHolder connectionHolder;

    @Autowired
    public JdbcProjectRepository(ConnectionHolder connectionHolder) {
        this.connectionHolder = connectionHolder;
    }

    @Override
    public List<Project> getAll() {
        List<Project> projects = new ArrayList<>();
        try (Connection connection = connectionHolder.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM projects");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Project project = new Project();
                project.setId(resultSet.getInt("id"));
                project.setTitle(resultSet.getString("title"));
                project.setDescription(resultSet.getString("description"));
                project.setStartDate(resultSet.getObject("start_date", LocalDateTime.class));
                project.setStatus(resultSet.getString("status"));
                project.setTeamId(resultSet.getInt("team_id"));
                project.setManagerId(resultSet.getInt("manager_id"));
                projects.add(project);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get all projects", e);
        }
        return projects;
    }

    @Override
    public Project save(Project project) {
        String sql = "INSERT INTO projects (id,title, description, start_date, status, team_id, manager_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = connectionHolder.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, project.getId());
            statement.setString(2, project.getTitle());
            statement.setString(3, project.getDescription());
            statement.setObject(4, project.getStartDate());
            statement.setString(5, project.getStatus());
            statement.setInt(6, project.getTeamId());
            statement.setInt(7, project.getManagerId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating project failed, no rows affected.");
            }
        }
        catch (SQLException e){
            throw new RuntimeException("Failed to save project", e);
        }
        return project;
    }

    @Override
    public Optional<Project> getById(int id) {
        String sql = "SELECT * FROM projects WHERE id = ?";
        try (Connection connection = connectionHolder.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Project project = new Project();
                    project.setId(resultSet.getInt("id"));
                    project.setTitle(resultSet.getString("title"));
                    project.setDescription(resultSet.getString("description"));
                    project.setStartDate(resultSet.getObject("start_date", LocalDateTime.class));
                    project.setStatus(resultSet.getString("status"));
                    project.setTeamId(resultSet.getInt("team_id"));
                    project.setManagerId(resultSet.getInt("manager_id"));
                    return Optional.of(project);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get project by id", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Project> update(int id, Project updatedProject) {
        String sql = "UPDATE projects SET title = ?, description = ?, start_date = ?, status = ?, team_id = ?, manager_id = ? WHERE id = ?";
        try (Connection connection = connectionHolder.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, updatedProject.getTitle());
            statement.setString(2, updatedProject.getDescription());
            statement.setObject(3, updatedProject.getStartDate());
            statement.setString(4, updatedProject.getStatus());
            statement.setInt(5, updatedProject.getTeamId());
            statement.setInt(6, updatedProject.getManagerId());
            statement.setInt(7, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                return Optional.empty();
            }
            return Optional.of(updatedProject);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update project", e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM projects WHERE id = ?";
        try (Connection connection = connectionHolder.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete project", e);
        }
    }
}
