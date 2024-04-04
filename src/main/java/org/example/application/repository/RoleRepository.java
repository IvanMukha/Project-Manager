package org.example.application.repository;

import org.example.application.repositoryInterfaces.RoleRepositoryInterface;
import org.example.application.model.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class RoleRepository implements RoleRepositoryInterface {
    private static final Logger log = LoggerFactory.getLogger(RoleRepository.class);
    List<Role> roles = new ArrayList<>();

    public List<Role> getAll() {
        return roles;
    }

    public Role save(Role role) {
        roles.add(role);
        return role;
    }

    public Optional<Role> getById(int id) {
        Optional<Role> optionalRole = roles.stream()
                .filter(role -> role.getId() == id)
                .findFirst();
        if (optionalRole.isEmpty()) {
            log.error("Object with id " + id + " does not exist");
        }
        return optionalRole;
    }

    public Optional<Role> update(int id, Role updatedRole) {
        Optional<Role> optionalRole = getById(id);
        optionalRole.ifPresent(role -> role.setName(updatedRole.getName()));
        return optionalRole;
    }

    public void delete(int id) {
        roles.removeIf(role -> role.getId() == id);
    }
}
