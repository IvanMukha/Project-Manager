package org.example.application.repository;

import org.example.application.repositoryInterfaces.UserRepositoryInterface;
import org.example.application.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository implements UserRepositoryInterface {
    private static final Logger log = LoggerFactory.getLogger(UserRepository.class);
    List<User> users = new ArrayList<>();

    public List<User> getAll() {
        return users;
    }

    public User save(User user) {
        users.add(user);
        return user;
    }

    public Optional<User> getById(int id) {
        Optional<User> optionalUser = users.stream()
                .filter(user -> user.getId() == id)
                .findFirst();
        if (optionalUser.isEmpty()) {
            log.error("Object with id " + id + " does not exist");
        }
        return optionalUser;
    }

    public Optional<User> update(int id, User updatedUser) {
        Optional<User> optionalUser = getById(id);
        optionalUser.ifPresent(user -> user.setUsername(updatedUser.getUsername()).setPassword(updatedUser.getPassword()).
                setEmail(updatedUser.getEmail()));
        return optionalUser;
    }

    public void delete(int id) {
        users.removeIf(user -> user.getId() == id);
    }
}
