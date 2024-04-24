package com.ivan.projectmanager.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivan.projectmanager.dto.RoleDTO;
import com.ivan.projectmanager.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/roles")
public class RoleController {
    private final RoleService roleService;
    private final ObjectMapper objectMapper;

    @Autowired
    public RoleController(RoleService roleService, ObjectMapper objectMapper) {
        this.roleService = roleService;
        this.objectMapper = objectMapper;
    }

    @GetMapping()
    public String getAll() throws JsonProcessingException {
        return objectMapper.writeValueAsString(roleService.getAll());
    }

    @PostMapping("/new")
    public String save(RoleDTO roleDTO) throws JsonProcessingException {
        return objectMapper.writeValueAsString(roleService.save(roleDTO));
    }

    @GetMapping("/{id}")
    public String getById(Long id) throws JsonProcessingException {
        Optional<RoleDTO> roleDTOOptional = roleService.getById(id);
        return objectMapper.writeValueAsString(roleDTOOptional.orElse(null));
    }

    @PatchMapping("/{id}")
    public String update(Long id, RoleDTO roleDTO) throws JsonProcessingException {
        return objectMapper.writeValueAsString(roleService.update(id, roleDTO));
    }

    @DeleteMapping("/{id}")
    public void delete(Long id) {
        roleService.delete(id);
    }
}
