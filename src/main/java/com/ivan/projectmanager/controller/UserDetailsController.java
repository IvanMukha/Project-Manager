package com.ivan.projectmanager.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivan.projectmanager.dto.UserDetailsDTO;
import com.ivan.projectmanager.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/users/{id}/userDetails")
public class UserDetailsController {
    private final UserDetailsService userDetailsService;
    private final ObjectMapper objectMapper;

    @Autowired
    public UserDetailsController(UserDetailsService userDetailsService, ObjectMapper objectMapper) {
        this.userDetailsService = userDetailsService;
        this.objectMapper = objectMapper;
    }

    @GetMapping()
    public String getAll() throws JsonProcessingException {
        return objectMapper.writeValueAsString(userDetailsService.getAll());
    }

    @PostMapping("/new")
    public String save(UserDetailsDTO userDetailsDTO) throws JsonProcessingException {
        return objectMapper.writeValueAsString(userDetailsService.save(userDetailsDTO));
    }

    @GetMapping("/{id}")
    public String getById(Long id) throws JsonProcessingException {
        Optional<UserDetailsDTO> userDetailsDTOOptional = userDetailsService.getById(id);
        return objectMapper.writeValueAsString(userDetailsDTOOptional.orElse(null));
    }

    @PatchMapping("/{id}")
    public String update(Long id, UserDetailsDTO userDetailsDTO) throws JsonProcessingException {
        return objectMapper.writeValueAsString(userDetailsService.update(id, userDetailsDTO));
    }

    @DeleteMapping("/{id}")
    public void delete(Long id) {
        userDetailsService.delete(id);
    }
}
