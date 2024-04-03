package org.example.application.repository;

import org.example.application.model.UserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDetailsRepository implements BaseRepository<UserDetails> {
    private final Logger log = LoggerFactory.getLogger(UserDetailsRepository.class);
    List<UserDetails> usersDetails = new ArrayList<>();

    public List<UserDetails> getAll() {
        return usersDetails;
    }

    public UserDetails save(UserDetails userDetails) {
        usersDetails.add(userDetails);
        return userDetails;
    }

    public Optional<UserDetails> getById(int id) {
        Optional<UserDetails> optionalUserDetails = usersDetails.stream()
                .filter(userDetails -> userDetails.getUserId() == id)
                .findFirst();
        if (optionalUserDetails.isEmpty()) {
            log.error("Object with id " + id + " does not exist");
        }
        return optionalUserDetails;
    }


    public Optional<UserDetails> update(int id, UserDetails updatedUserDetails) {
        Optional<UserDetails> optionalUserDetails = getById(id);
        optionalUserDetails.ifPresent(userDetails -> userDetails.setName(updatedUserDetails.getName()).setSurname(updatedUserDetails.getSurname()).
                setPhone(updatedUserDetails.getPhone()).setWorkPhone(updatedUserDetails.getWorkPhone()).
                setWorkAddress(updatedUserDetails.getWorkAddress()).setDepartment(updatedUserDetails.getDepartment()));
        return optionalUserDetails;
    }

    public void delete(int userId) {
        usersDetails.removeIf(userDetails -> userDetails.getUserId() == userId);
    }
}
