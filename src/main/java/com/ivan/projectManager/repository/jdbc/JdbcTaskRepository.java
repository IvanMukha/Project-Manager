package com.ivan.projectManager.repository.jdbc;

import com.ivan.projectManager.model.Task;
import com.ivan.projectManager.repository.TaskRepository;
import com.ivan.projectManager.utils.ConnectionHolder;
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
public class JdbcTaskRepository implements TaskRepository {

    private final ConnectionHolder connectionHolder;


    public JdbcTaskRepository(ConnectionHolder connectionHolder) {
        this.connectionHolder = connectionHolder;

    }

    @Override
    public List<Task> getAll() {
        List<Task> tasks = new ArrayList<>();
        try (Connection connection = connectionHolder.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM tasks");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Task task = new Task();
                task.setId(resultSet.getInt("id"));
                task.setTitle(resultSet.getString("title"));
                task.setStatus(resultSet.getString("status"));
                task.setPriority(resultSet.getString("priority"));
                task.setStartDate(resultSet.getObject("start_date", LocalDateTime.class));
                task.setDueDate(resultSet.getObject("due_date", LocalDateTime.class));
                task.setReporter(resultSet.getInt("reporter_id"));
                task.setAssignee(resultSet.getInt("assignee_id"));
                task.setCategory(resultSet.getString("category"));
                task.setLabel(resultSet.getString("label"));
                task.setDescription(resultSet.getString("description"));
                task.setProjectId(resultSet.getInt("project_id"));
                tasks.add(task);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get all tasks", e);
        }
        connectionHolder.releaseConnection();
        return tasks;
    }

    @Override
    public Task save(Task task) {
        String sql = "INSERT INTO tasks (id,title, status, priority, start_date, due_date, reporter_id, assignee_id, category, label, description, project_id) VALUES (?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = connectionHolder.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, task.getId());
            statement.setString(2, task.getTitle());
            statement.setString(3, task.getStatus());
            statement.setString(4, task.getPriority());
            statement.setObject(5, task.getStartDate());
            statement.setObject(6, task.getDueDate());
            statement.setInt(7, task.getReporter());
            statement.setInt(8, task.getAssignee());
            statement.setString(9, task.getCategory());
            statement.setString(10, task.getLabel());
            statement.setString(11, task.getDescription());
            statement.setInt(12, task.getProjectId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating task failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save task", e);
        }
        return task;
    }

    @Override
    public Optional<Task> getById(int id) {
        String sql = "SELECT * FROM tasks WHERE id = ?";
        try (Connection connection = connectionHolder.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Task task = new Task();
                    task.setId(resultSet.getInt("id"));
                    task.setTitle(resultSet.getString("title"));
                    task.setStatus(resultSet.getString("status"));
                    task.setPriority(resultSet.getString("priority"));
                    task.setStartDate(resultSet.getObject("start_date", LocalDateTime.class));
                    task.setDueDate(resultSet.getObject("due_date", LocalDateTime.class));
                    task.setReporter(resultSet.getInt("reporter_id"));
                    task.setAssignee(resultSet.getInt("assignee_id"));
                    task.setCategory(resultSet.getString("category"));
                    task.setLabel(resultSet.getString("label"));
                    task.setDescription(resultSet.getString("description"));
                    task.setProjectId(resultSet.getInt("project_id"));
                    return Optional.of(task);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get task by id", e);
        }
        connectionHolder.releaseConnection();
        return Optional.empty();
    }

    @Override
    public Optional<Task> update(int id, Task updatedTask) {
        String sql = "UPDATE tasks SET title = ?, status = ?, priority = ?, due_date = ?,category = ?, label = ?, description = ? WHERE id = ?";
        try (Connection connection = connectionHolder.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, updatedTask.getTitle());
            statement.setString(2, updatedTask.getStatus());
            statement.setString(3, updatedTask.getPriority());
            statement.setObject(4, updatedTask.getDueDate());
            statement.setString(5, updatedTask.getCategory());
            statement.setString(6, updatedTask.getLabel());
            statement.setString(7, updatedTask.getDescription());
            statement.setInt(8, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                return Optional.empty();
            }
            return Optional.of(updatedTask);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update task", e);
        }
    }


    @Override
    public void delete(int id) {
        String sql = "DELETE FROM tasks WHERE id = ?";
        try (Connection connection = connectionHolder.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete task", e);
        }
    }
}
