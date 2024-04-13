package com.ivan.projectManager.repository.jdbc;

import com.ivan.projectManager.model.User;
import com.ivan.projectManager.repository.UserRepository;
import com.ivan.projectManager.repository.repositoryMapper.RowMapper;
import com.ivan.projectManager.repository.repositoryMapper.UserRowMapper;
import com.ivan.projectManager.utils.ConnectionHolder;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class JdbcUserRepository implements UserRepository {

    private final ConnectionHolder connectionHolder;
    private final RowMapper<User> rowMapper;

    public JdbcUserRepository(ConnectionHolder connectionHolder, RowMapper<User> rowMapper) {
        this.connectionHolder = connectionHolder;
        this.rowMapper=rowMapper;
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = connectionHolder.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM users")) {
            users=rowMapper.mapAll(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get All Users", e);
        }
        connectionHolder.releaseConnection();
        return users;
    }

    @Override
    public User save(User user) {
        String sql = "INSERT INTO users (id, username, password, email) VALUES (?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionHolder.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, user.getId());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getEmail());
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save user", e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    throw new RuntimeException("failed to close statement",e);
                }
            }
        }
        return user;
    }




    @Override
    public Optional<User> getById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection connection = connectionHolder.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return Optional.ofNullable(rowMapper.map(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get user by id", e);
        } finally {
            connectionHolder.releaseConnection();
        }
    }


    @Override
    public Optional<User> update(int id, User updatedUser) {
        String sql = "UPDATE users SET username = ?, email = ? WHERE id = ?";
        try (Connection connection = connectionHolder.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, updatedUser.getUsername());
            statement.setString(2, updatedUser.getEmail());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                return Optional.empty();
            }
            return Optional.of(updatedUser);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update user", e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection connection = connectionHolder.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete user", e);
        }
    }
}
