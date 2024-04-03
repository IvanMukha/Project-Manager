package org.example.application.repository;

import org.example.application.model.User;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    List<User> users = new ArrayList<>();

    public void save(User user) {
        users.add(user);
    }

    public List<User> getAll() {
        return new ArrayList<>(users);
    }

    public Optional<User> getById(int id) {
        return users.stream().filter(user -> user.getId() == id).findFirst();
    }

    public void update(int id, User updatedUser) {
        Optional<User> optionalUser = getById(id);
        optionalUser.ifPresent(user -> user.setUsername(updatedUser.getUsername()).setPassword(updatedUser.getPassword()).
                setEmail(updatedUser.getEmail()));
    }

    public void delete(int id) {
        users.removeIf(user -> user.getId() == id);
    }
}
