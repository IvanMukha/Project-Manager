package com.ivan.projectmanager.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivan.projectmanager.dto.TeamDTO;
import com.ivan.projectmanager.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/teams")
public class TeamController {
    private final TeamService teamService;
    private final ObjectMapper objectMapper;

    @Autowired
    public TeamController(TeamService teamService, ObjectMapper objectMapper) {
        this.teamService = teamService;
        this.objectMapper = objectMapper;
    }

    @GetMapping()
    public String getAll() throws JsonProcessingException {
        return objectMapper.writeValueAsString(teamService.getAll());
    }

    @PostMapping("/new")
    public String save(TeamDTO teamDTO) throws JsonProcessingException {
        return objectMapper.writeValueAsString(teamService.save(teamDTO));
    }

    @GetMapping("/{id}")
    public String getById(Long id) throws JsonProcessingException {
        Optional<TeamDTO> teamDTOOptional = teamService.getById(id);
        return objectMapper.writeValueAsString(teamDTOOptional.orElse(null));
    }

    @PatchMapping("{id}")
    public String update(Long id, TeamDTO teamDTO) throws JsonProcessingException {
        return objectMapper.writeValueAsString(teamService.update(id, teamDTO));
    }

    @DeleteMapping("/{id}")
    public void delete(Long id) {
        teamService.delete(id);
    }
}
