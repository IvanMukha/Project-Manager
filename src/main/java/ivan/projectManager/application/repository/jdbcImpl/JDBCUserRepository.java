package ivan.projectManager.application.repository.jdbcImpl;

import ivan.projectManager.application.model.User;
import ivan.projectManager.application.repository.UserRepository;
import ivan.projectManager.application.utils.ConnectionHolder;
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
public class JDBCUserRepository implements UserRepository {

    private final ConnectionHolder connectionHolder;

    public JDBCUserRepository(ConnectionHolder connectionHolder) {
        this.connectionHolder = connectionHolder;
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = connectionHolder.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM users")) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get All Users", e);
        }
        return users;
    }

    @Override
    public User save(User user) {
        String sql = "INSERT INTO users (id, username, password, email) VALUES (?, ?, ?, ?)";
        try (Connection connection = connectionHolder.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
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
                if (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setUsername(resultSet.getString("username"));
                    user.setEmail(resultSet.getString("email"));
                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get user by id", e);
        }
        return Optional.empty();
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
