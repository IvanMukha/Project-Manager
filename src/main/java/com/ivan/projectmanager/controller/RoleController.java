package com.ivan.projectmanager.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivan.projectmanager.dto.RoleDTO;
import com.ivan.projectmanager.service.RoleService;
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
@RequestMapping("/roles")
public class RoleController {
    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping()
    public ResponseEntity<List<RoleDTO>> getAll() {
        List<RoleDTO> roles = roleService.getAll();
        return ResponseEntity.ok().body(roles);
    }

    @PostMapping("/new")
    public ResponseEntity<RoleDTO> save(@RequestBody RoleDTO roleDTO) {
        RoleDTO savedRole = roleService.save(roleDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRole);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> getById(@PathVariable("id") Long id) {
        Optional<RoleDTO> roleDTOOptional = roleService.getById(id);
        return roleDTOOptional.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RoleDTO> update(@PathVariable("id") Long id, @RequestBody RoleDTO roleDTO) {
        Optional<RoleDTO> updatedRole = roleService.update(id, roleDTO);
        return updatedRole.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        roleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}