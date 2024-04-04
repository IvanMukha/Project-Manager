package org.example.application.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.application.service.RoleService;
import org.example.application.dto.RoleDTO;
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

    public String getById(int id) throws JsonProcessingException {
        Optional<RoleDTO> roleDTOOptional = roleService.getById(id);
        return objectMapper.writeValueAsString(roleDTOOptional.orElse(null));
    }

    public String update(int id, RoleDTO roleDTO) throws JsonProcessingException {
        return objectMapper.writeValueAsString(roleService.update(id, roleDTO));
    }

    public void delete(int id) {
        roleService.delete(id);
    }
}
