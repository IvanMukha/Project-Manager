package com.ivan.projectmanager.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivan.projectmanager.dto.UserDTO;
import com.ivan.projectmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final ObjectMapper objectMapper;

    @Autowired
    public UserController(UserService userService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    @GetMapping()
    public String getAll() throws JsonProcessingException {
        return objectMapper.writeValueAsString(userService.getAll());
    }

    @PostMapping("/new")
    public String save(UserDTO userDTO) throws JsonProcessingException {
        return objectMapper.writeValueAsString(userService.save(userDTO));
    }

    @GetMapping("/{id}")
    public String getById(Long id) throws JsonProcessingException {
        Optional<UserDTO> userDTOOptional = userService.getById(id);
        return objectMapper.writeValueAsString(userDTOOptional.orElse(null));
    }

    @PatchMapping("/{id}")
    public String update(Long id, UserDTO userDTO) throws JsonProcessingException {
        return objectMapper.writeValueAsString(userService.update(id, userDTO));
    }

    @DeleteMapping("/{id}")
    public void delete(Long id) {
        userService.delete(id);
    }
}
