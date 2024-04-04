package org.example.application.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.application.ServiceInterfaces.UserServiceInterface;
import org.example.application.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class UserController {
    private final UserServiceInterface userService;
    private final ObjectMapper objectMapper;

    @Autowired
    public UserController(UserServiceInterface userService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    public String getAll() throws JsonProcessingException {
        return objectMapper.writeValueAsString(userService.getAll());
    }

    public String save(UserDTO userDTO) throws JsonProcessingException {
        return objectMapper.writeValueAsString(userService.save(userDTO));
    }

    public String getById(int id) throws JsonProcessingException {
        Optional<UserDTO> userDTOOptional = userService.getById(id);
        return objectMapper.writeValueAsString(userDTOOptional.orElse(null));
    }

    public String update(int id, UserDTO userDTO) throws JsonProcessingException {
        return objectMapper.writeValueAsString(userService.update(id, userDTO));
    }

    public void delete(int id) {
        userService.delete(id);
    }
}
