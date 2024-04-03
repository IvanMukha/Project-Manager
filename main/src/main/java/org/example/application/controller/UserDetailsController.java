package org.example.application.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.application.dto.UserDetailsDTO;
import org.example.application.service.UserDetailsService;
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

    public void save(UserDetailsDTO userDetailsDTO) {
        userDetailsService.save(userDetailsDTO);
    }

    public String getById(int id) throws JsonProcessingException {
        Optional<UserDetailsDTO> userDetailsOptional = userDetailsService.getById(id);
        return objectMapper.writeValueAsString(userDetailsOptional.orElse(null));
    }

    public String getAll() throws JsonProcessingException {
        return objectMapper.writeValueAsString(userDetailsService.getAll());
    }

    public void update(int id, UserDetailsDTO userDetailsDTO) {
        userDetailsService.update(id, userDetailsDTO);
    }

    public void delete(int id) {
        userDetailsService.delete(id);
    }
}
