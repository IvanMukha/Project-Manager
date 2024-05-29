package com.ivan.projectmanager.controller;

import com.ivan.projectmanager.dto.UserDetailsDTO;
import com.ivan.projectmanager.service.UserDetailsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/users/{userId}")
@Validated
public class UserDetailsController {
    private final UserDetailsService userDetailsService;


    @Autowired
    public UserDetailsController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/userDetails")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<UserDetailsDTO> save(@PathVariable("userId") Long userId,
                                               @RequestBody @Valid UserDetailsDTO userDetailsDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        UserDetailsDTO savedUserDetails = userDetailsService.save(userId, userDetailsDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUserDetails);
    }

    @GetMapping("/userDetails")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<UserDetailsDTO> getById(@PathVariable("userId") Long id) {
        Optional<UserDetailsDTO> userDetailsDTOOptional = userDetailsService.getById(id);
        return userDetailsDTOOptional.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/userDetails")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<UserDetailsDTO> update(@PathVariable("userId") Long id, @RequestBody @Valid UserDetailsDTO userDetailsDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Optional<UserDetailsDTO> updatedUserDetails = userDetailsService.update(id, userDetailsDTO);
        return updatedUserDetails.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/userDetails")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable("userId") Long id) {
        userDetailsService.delete(id);
        return ResponseEntity.noContent().build();
    }
}