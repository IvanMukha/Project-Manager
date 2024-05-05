package com.ivan.projectmanager.controller;

import com.ivan.projectmanager.dto.TeamDTO;
import com.ivan.projectmanager.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teams")
@Validated
public class TeamController {
    private final TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    @GetMapping()
    public ResponseEntity<List<TeamDTO>> getAll() {
        List<TeamDTO> teams = teamService.getAll();
        return ResponseEntity.ok().body(teams);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping()
    public ResponseEntity<TeamDTO> save(@RequestBody TeamDTO teamDTO) {
        TeamDTO savedTeam = teamService.save(teamDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTeam);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<TeamDTO> getById(@PathVariable("id") Long id) {
        Optional<TeamDTO> teamDTOOptional = teamService.getById(id);
        return teamDTOOptional.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<TeamDTO> update(@PathVariable("id") Long id, @RequestBody TeamDTO teamDTO) {
        Optional<TeamDTO> updatedTeam = teamService.update(id, teamDTO);
        return updatedTeam.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        teamService.delete(id);
        return ResponseEntity.noContent().build();
    }
}