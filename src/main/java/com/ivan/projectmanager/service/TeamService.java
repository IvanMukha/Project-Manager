package com.ivan.projectmanager.service;

import com.ivan.projectmanager.dto.TeamDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface TeamService {
    Page<TeamDTO> getAll(Integer page, Integer size);

    TeamDTO save(TeamDTO teamDTO);

    Optional<TeamDTO> getById(Long id);

    Optional<TeamDTO> update(Long id, TeamDTO updatedTeamDTO);

    void delete(Long id);
}
