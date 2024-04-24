package com.ivan.projectmanager.service.impl;

import com.ivan.projectmanager.dto.TeamDTO;
import com.ivan.projectmanager.exeptions.HandleCustomIllegalArgumentException;
import com.ivan.projectmanager.exeptions.HandleCustomNullPointerException;
import com.ivan.projectmanager.model.Team;
import com.ivan.projectmanager.repository.TeamRepository;
import com.ivan.projectmanager.service.TeamService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeamServiceImpl implements TeamService {
    private final ModelMapper modelMapper;
    private final TeamRepository teamRepository;

    @Autowired
    public TeamServiceImpl(ModelMapper modelMapper, TeamRepository teamRepository) {
        this.modelMapper = modelMapper;
        this.teamRepository = teamRepository;
    }

    public List<TeamDTO> getAll() {
        return teamRepository.getAll().stream().map(this::mapTeamToDTO).collect(Collectors.toList());
    }

    @Transactional
    public TeamDTO save(TeamDTO teamDTO) {
        checkTeam(teamDTO);
        return mapTeamToDTO(teamRepository.save(mapDTOToTeam(teamDTO)));
    }

    public Optional<TeamDTO> getById(Long id) {
        checkId(id);
        Optional<Team> teamOptional = teamRepository.getById(id);
        return teamOptional.map(this::mapTeamToDTO);
    }

    @Transactional
    public Optional<TeamDTO> update(Long id, TeamDTO updatedTeamDTO) {
        checkId(id);
        checkTeam(updatedTeamDTO);
        Optional<Team> teamOptional = teamRepository.update(id, mapDTOToTeam(updatedTeamDTO));
        return teamOptional.map(this::mapTeamToDTO);
    }

    @Transactional
    public void delete(Long id) {
        checkId(id);
        teamRepository.delete(id);
    }

    private void checkTeam(TeamDTO teamDTO) {
        if (teamDTO == null) {
            throw new HandleCustomNullPointerException("TeamDTO cannot be null");
        }
        if (teamDTO.getName() == null) {
            throw new HandleCustomNullPointerException("Team name cannot be null");
        }
        if (teamDTO.getName().isEmpty()) {
            throw new HandleCustomIllegalArgumentException("Team name cannot be empty");
        }
    }

    private void checkId(Long id) {
        if (id == null) {
            throw new HandleCustomNullPointerException("Team id cannot be null");
        }
        if (id <= 0) {
            throw new HandleCustomIllegalArgumentException("Team id must be greater than 0");
        }
    }

    private Team mapDTOToTeam(TeamDTO teamDTO) {
        return modelMapper.map(teamDTO, Team.class);
    }

    private TeamDTO mapTeamToDTO(Team team) {
        return modelMapper.map(team, TeamDTO.class);
    }
}


