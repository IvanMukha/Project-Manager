package com.ivan.projectmanager.repository.rowmapper;

import com.ivan.projectmanager.model.User;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserRowMapper implements RowMapper<User> {
    public List<User> mapAll(ResultSet resultSet) {
        List<User> users = new ArrayList<>();
        try {
            while (resultSet.next()) {
                User user = map(resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to map users", e);
        }
        return users;
    }

    public User map(ResultSet resultSet) {
        try {
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                return user;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to map user", e);
        }
        return null;
    }
}

