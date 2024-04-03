package org.example.application.repository;

import org.example.application.model.UserDetails;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDetailsRepository {
    List<UserDetails> usersDetails = new ArrayList<>();

    public void save(UserDetails userDetails) {
        usersDetails.add(userDetails);
    }

    public List<UserDetails> getAll() {
        return new ArrayList<>(usersDetails);
    }

    public Optional<UserDetails> getById(int userId) {
        return usersDetails.stream().filter(userDetails -> userDetails.getUserId() == userId).findFirst();
    }

    public void update(int id, UserDetails updatedUserDetails) {
        Optional<UserDetails> optionalUserDetails = getById(id);
        optionalUserDetails.ifPresent(userDetails -> userDetails.setName(updatedUserDetails.getName()).setSurname(updatedUserDetails.getSurname()).
                setPhone(updatedUserDetails.getPhone()).setWorkPhone(updatedUserDetails.getWorkPhone()).
                setWorkAddress(updatedUserDetails.getWorkAddress()).setDepartment(updatedUserDetails.getDepartment()));
    }

    public void delete(int userId) {
        usersDetails.removeIf(userDetails -> userDetails.getUserId() == userId);
    }
}
