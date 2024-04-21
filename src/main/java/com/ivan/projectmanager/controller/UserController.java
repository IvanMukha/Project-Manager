package com.ivan.projectmanager.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivan.projectmanager.dto.UserDTO;
import com.ivan.projectmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class UserController {
    private final UserService userService;
    private final ObjectMapper objectMapper;

    @Autowired
    public UserController(UserService userService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    public String getAll() throws JsonProcessingException {
        return objectMapper.writeValueAsString(userService.getAll());
    }

    public String save(UserDTO userDTO) throws JsonProcessingException {
        return objectMapper.writeValueAsString(userService.save(userDTO));
    }

    public String getById(Long id) throws JsonProcessingException {
        Optional<UserDTO> userDTOOptional = userService.getById(id);
        return objectMapper.writeValueAsString(userDTOOptional.orElse(null));
    }

    public String update(Long id, UserDTO userDTO) throws JsonProcessingException {
        return objectMapper.writeValueAsString(userService.update(id, userDTO));
    }

    public void delete(Long id) {
        userService.delete(id);
    }
}
