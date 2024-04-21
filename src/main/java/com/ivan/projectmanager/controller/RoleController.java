package com.ivan.projectmanager.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivan.projectmanager.dto.RoleDTO;
import com.ivan.projectmanager.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class RoleController {
    private final RoleService roleService;
    private final ObjectMapper objectMapper;

    @Autowired
    public RoleController(RoleService roleService, ObjectMapper objectMapper) {
        this.roleService = roleService;
        this.objectMapper = objectMapper;
    }

    public String getAll() throws JsonProcessingException {
        return objectMapper.writeValueAsString(roleService.getAll());
    }

    public String save(RoleDTO roleDTO) throws JsonProcessingException {
        return objectMapper.writeValueAsString(roleService.save(roleDTO));
    }

    public String getById(Long id) throws JsonProcessingException {
        Optional<RoleDTO> roleDTOOptional = roleService.getById(id);
        return objectMapper.writeValueAsString(roleDTOOptional.orElse(null));
    }

    public String update(Long id, RoleDTO roleDTO) throws JsonProcessingException {
        return objectMapper.writeValueAsString(roleService.update(id, roleDTO));
    }

    public void delete(Long id) {
        roleService.delete(id);
    }
}
