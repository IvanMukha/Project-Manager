package com.ivan.projectmanager.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivan.projectmanager.dto.UserDetailsDTO;
import com.ivan.projectmanager.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users/{userId}")
public class UserDetailsController {
    private final UserDetailsService userDetailsService;


    @Autowired
    public UserDetailsController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/userDetails/new")
    public ResponseEntity<UserDetailsDTO> save(@RequestBody UserDetailsDTO userDetailsDTO) {
        UserDetailsDTO savedUserDetails = userDetailsService.save(userDetailsDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUserDetails);
    }

    @GetMapping("/userDetails")
    public ResponseEntity<UserDetailsDTO> getById(@PathVariable("userId") Long id) {
        Optional<UserDetailsDTO> userDetailsDTOOptional = userDetailsService.getById(id);
        return userDetailsDTOOptional.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/userDetails")
    public ResponseEntity<UserDetailsDTO> update(@PathVariable("userId") Long id, @RequestBody UserDetailsDTO userDetailsDTO) {
        Optional<UserDetailsDTO> updatedUserDetails = userDetailsService.update(id, userDetailsDTO);
        return updatedUserDetails.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/userDetails")
    public ResponseEntity<Void> delete(@PathVariable("userId") Long id) {
        userDetailsService.delete(id);
        return ResponseEntity.noContent().build();
    }
}