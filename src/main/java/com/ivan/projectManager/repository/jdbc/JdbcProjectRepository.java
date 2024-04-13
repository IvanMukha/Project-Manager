package com.ivan.projectManager.repository.jdbc;

import com.ivan.projectManager.model.Project;
import com.ivan.projectManager.repository.ProjectRepository;
import com.ivan.projectManager.repository.repositoryMapper.ProjectRowMapper;
import com.ivan.projectManager.repository.repositoryMapper.RowMapper;
import com.ivan.projectManager.utils.ConnectionHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class JdbcProjectRepository implements ProjectRepository {

    private final ConnectionHolder connectionHolder;
    private final RowMapper<Project> rowMapper;

    @Autowired
    public JdbcProjectRepository(ConnectionHolder connectionHolder, RowMapper<Project> rowMapper) {
        this.connectionHolder = connectionHolder;
        this.rowMapper=rowMapper;
    }

    @Override
    public List<Project> getAll() {
        List<Project> projects = new ArrayList<>();
        try (Connection connection = connectionHolder.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM projects");
             ResultSet resultSet = statement.executeQuery()) {
            projects = rowMapper.mapAll(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get all projects", e);
        } finally {
            connectionHolder.releaseConnection();
        }
        return projects;
    }

    @Override
    public Project save(Project project) {
        String sql = "INSERT INTO projects (id, title, description, start_date, status, team_id, manager_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionHolder.getConnection();
            statement = connection.prepareStatement(sql);
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
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save project", e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    throw new RuntimeException("Failed to close statement", e);
                }
            }
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
              return Optional.ofNullable(rowMapper.map(resultSet));
                }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get project by id", e);
        }
        finally {
            connectionHolder.releaseConnection();
        }
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
