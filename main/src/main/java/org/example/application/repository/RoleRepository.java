package org.example.application.repository;

import org.example.application.model.Role;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class RoleRepository {
    List<Role> roles = new ArrayList<>();

    public void save(Role role) {
        roles.add(role);
    }

    public Optional<Role> getById(int id) {
        return roles.stream().filter(role -> role.getId() == id).findFirst();
    }

    public List<Role> getAll() {
        return new ArrayList<>(roles);
    }

    public void update(int id, Role updatedRole) {
        Optional<Role> optionalRole = getById(id);
        optionalRole.ifPresent(role -> role.setName(updatedRole.getName()));
    }

    public void delete(int id) {
        roles.removeIf(role -> role.getId() == id);
    }
}
