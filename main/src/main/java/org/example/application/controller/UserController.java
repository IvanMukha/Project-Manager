package org.example.application.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.application.dto.UserDTO;
import org.example.application.service.UserService;
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

    public void save(UserDTO userDTO) {
        userService.save(userDTO);
    }

    public String getById(int id) throws JsonProcessingException {
        Optional<UserDTO> userOptional = userService.getById(id);
        return objectMapper.writeValueAsString(userOptional.orElse(null));
    }

    public String getAll() throws JsonProcessingException {
        return objectMapper.writeValueAsString(userService.getAll());
    }

    public void update(int id, UserDTO userDTO) {
        userService.update(id, userDTO);
    }

    public void delete(int id) {
        userService.delete(id);
    }
}
