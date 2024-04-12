package com.ivan.projectManager.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivan.projectManager.dto.UserDetailsDTO;
import com.ivan.projectManager.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class UserDetailsController {
    private final UserDetailsService userDetailsService;
    private final ObjectMapper objectMapper;

    @Autowired
    public UserDetailsController(UserDetailsService userDetailsService, ObjectMapper objectMapper) {
        this.userDetailsService = userDetailsService;
        this.objectMapper = objectMapper;
    }

    public String getAll() throws JsonProcessingException {
        return objectMapper.writeValueAsString(userDetailsService.getAll());
    }

    public String save(UserDetailsDTO userDetailsDTO) throws JsonProcessingException {
        return objectMapper.writeValueAsString(userDetailsService.save(userDetailsDTO));
    }

    public String getById(int id) throws JsonProcessingException {
        Optional<UserDetailsDTO> userDetailsDTOOptional = userDetailsService.getById(id);
        return objectMapper.writeValueAsString(userDetailsDTOOptional.orElse(null));
    }

    public String update(int id, UserDetailsDTO userDetailsDTO) throws JsonProcessingException {
        return objectMapper.writeValueAsString(userDetailsService.update(id, userDetailsDTO));
    }

    public void delete(int id) {
        userDetailsService.delete(id);
    }
}
