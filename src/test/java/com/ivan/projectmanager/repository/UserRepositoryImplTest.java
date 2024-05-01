package com.ivan.projectmanager.repository;


import com.ivan.projectmanager.config.ApplicationConfig;
import com.ivan.projectmanager.model.User;
import com.ivan.projectmanager.repository.impl.AttachmentRepositoryImpl;
import com.ivan.projectmanager.repository.impl.UserRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ApplicationConfig.class, UserRepositoryImpl.class, TestRepositoryConfiguration.class})
public class UserRepositoryImplTest {

    @Autowired
    private UserRepositoryImpl userRepository;

    @Test
    @Sql("classpath:data/userrepositorytests/insert-users.sql")
    public void testGetAll() {
        List<User> users = userRepository.getAll();
        assertThat(users).isNotEmpty();
    }

    @Test
    @Sql("classpath:data/userrepositorytests/insert-users.sql")
    public void testGetById() {
        Optional<User> user = userRepository.getById(1L);
        assertTrue(user.isPresent());
        assertEquals("user1", user.get().getUsername());
    }

    @Test
    @Sql("classpath:data/userrepositorytests/insert-users.sql")
    public void testDeleteUser() {
        Optional<User> user = userRepository.getById(1L);
        assertTrue(user.isPresent());
        userRepository.delete(1L);
        assertFalse(userRepository.getById(1L).isPresent());
    }

    @Test
    @Sql("classpath:data/userrepositorytests/insert-users.sql")
    public void testUpdate() {
        Optional<User> user = userRepository.getById(1L);
        assertTrue(user.isPresent());
        User updatedUser = user.get();
        updatedUser.setUsername("updatedUser");
        userRepository.update(1L, updatedUser);
        Optional<User> updatedUserOptional = userRepository.getById(1L);
        assertTrue(updatedUserOptional.isPresent());
        assertEquals("updatedUser", updatedUserOptional.get().getUsername());
    }

    @Test
    @Sql("classpath:data/userrepositorytests/insert-users.sql")
    public void testGetByUsernameCriteria() {
        List<User> foundUsers = userRepository.getByUsernameCriteria("user2");
        assertThat(foundUsers).isNotEmpty();
        assertThat(foundUsers.getFirst().getUsername()).isEqualTo("user2");
    }

    @Test
    @Sql("classpath:data/userrepositorytests/insert-users.sql")
    public void testGetByUsernameJPQL() {
        List<User> foundUsers = userRepository.getByUsernameJPQL("user2");
        assertThat(foundUsers).isNotEmpty();
        assertThat(foundUsers.getFirst().getUsername()).isEqualTo("user2");
    }

    @Test
    @Sql("classpath:data/userrepositorytests/insert-users.sql")
    public void testGetAllJpqlFetch() {
        List<User> foundUsers = userRepository.getAllJpqlFetch();
        assertThat(foundUsers).isNotEmpty();
        assertThat(foundUsers.getFirst().getRoles()).isNotEmpty();
        assertThat(foundUsers.getFirst().getTeams()).isNotEmpty();
    }

    @Test
    @Sql("classpath:data/userrepositorytests/insert-users.sql")
    public void testGetAllCriteriaFetch() {
        List<User> foundUsers = userRepository.getAllCriteriaFetch();
        assertThat(foundUsers).isNotEmpty();
        assertThat(foundUsers.getFirst().getRoles()).isNotEmpty();
        assertThat(foundUsers.getFirst().getTeams()).isNotEmpty();
    }

    @Test
    @Sql("classpath:data/userrepositorytests/insert-users.sql")
    public void testGetAllGraphFetch() {
        List<User> foundUsers = userRepository.getAllGraphFetch();
        assertThat(foundUsers).isNotEmpty();
        assertThat(foundUsers.getFirst().getRoles()).isNotEmpty();
        assertThat(foundUsers.getFirst().getTeams()).isNotEmpty();
    }
}