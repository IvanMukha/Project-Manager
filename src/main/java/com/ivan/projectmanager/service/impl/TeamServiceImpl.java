package com.ivan.projectmanager.service.impl;

import com.ivan.projectmanager.dto.TeamDTO;
import com.ivan.projectmanager.exeptions.CustomNotFoundException;
import com.ivan.projectmanager.model.Team;
import com.ivan.projectmanager.repository.TeamRepository;
import com.ivan.projectmanager.service.TeamService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TeamServiceImpl implements TeamService {
    private final ModelMapper modelMapper;
    private final TeamRepository teamRepository;

    @Autowired
    public TeamServiceImpl(ModelMapper modelMapper, TeamRepository teamRepository) {
        this.modelMapper = modelMapper;
        this.teamRepository = teamRepository;
    }

    public Page<TeamDTO> getAll(Integer page, Integer size) {
        if (page < 0) {
            page = 0;
        }
        if (size <= 0 || size > 100) {
            size = 10;
        }
        return teamRepository.getAll(PageRequest.of(page, size)).map(this::mapTeamToDTO);
    }

    @Transactional
    public TeamDTO save(TeamDTO teamDTO) {
        return mapTeamToDTO(teamRepository.save(mapDTOToTeam(teamDTO)));
    }

    public Optional<TeamDTO> getById(Long id) {
        Optional<Team> teamOptional = teamRepository.getById(id);
        if (teamOptional.isEmpty()) {
            throw new CustomNotFoundException(id, Team.class);
        }
        return teamOptional.map(this::mapTeamToDTO);
    }

    @Transactional
    public Optional<TeamDTO> update(Long id, TeamDTO updatedTeamDTO) {
        Optional<Team> teamOptional = teamRepository.update(id, mapDTOToTeam(updatedTeamDTO));
        if (teamOptional.isEmpty()) {
            throw new CustomNotFoundException(id, Team.class);
        }
        return teamOptional.map(this::mapTeamToDTO);
    }

    @Transactional
    public void delete(Long id) {
        teamRepository.delete(id);
    }

    private Team mapDTOToTeam(TeamDTO teamDTO) {
        return modelMapper.map(teamDTO, Team.class);
    }

    private TeamDTO mapTeamToDTO(Team team) {
        return modelMapper.map(team, TeamDTO.class);
    }
}


