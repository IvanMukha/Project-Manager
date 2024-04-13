package com.ivan.projectManager.repository.repositoryMapper;

import com.ivan.projectManager.model.Task;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Component
public class TaskRowMapper implements RowMapper<Task> {
    public List<Task> mapAll(ResultSet resultSet) {
        List<Task> tasks = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Task task =map(resultSet);;

                tasks.add(task);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to map tasks", e);
        }
        return tasks;
    }

    public Task map(ResultSet resultSet) {
        try {
            if (resultSet.next()) {
                Task task = new Task();
                mapTask(resultSet, task);
                return task;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to map task", e);
        }
        return null;
    }

    private void mapTask(ResultSet resultSet, Task task) throws SQLException {
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
    }
}

