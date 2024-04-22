package com.ivan.projectmanager.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivan.projectmanager.dto.TeamDTO;
import com.ivan.projectmanager.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class TeamController {
    private final TeamService teamService;
    private final ObjectMapper objectMapper;

    @Autowired
    public TeamController(TeamService teamService, ObjectMapper objectMapper) {
        this.teamService = teamService;
        this.objectMapper = objectMapper;
    }

    public String getAll() throws JsonProcessingException {
        return objectMapper.writeValueAsString(teamService.getAll());
    }

    public String save(TeamDTO teamDTO) throws JsonProcessingException {
        return objectMapper.writeValueAsString(teamService.save(teamDTO));
    }

    public String getById(Long id) throws JsonProcessingException {
        Optional<TeamDTO> teamDTOOptional = teamService.getById(id);
        return objectMapper.writeValueAsString(teamDTOOptional.orElse(null));
    }

    public String update(Long id, TeamDTO teamDTO) throws JsonProcessingException {
        return objectMapper.writeValueAsString(teamService.update(id, teamDTO));
    }

    public void delete(Long id) {
        teamService.delete(id);
    }
}
