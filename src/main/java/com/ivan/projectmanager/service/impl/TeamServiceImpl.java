package com.ivan.projectmanager.service.impl;

import com.ivan.projectmanager.dto.TeamDTO;
import com.ivan.projectmanager.model.Team;
import com.ivan.projectmanager.repository.TeamRepository;
import com.ivan.projectmanager.service.TeamService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public TeamDTO save(TeamDTO teamDTO) {
        return mapTeamToDTO(teamRepository.save(mapDTOToTeam(teamDTO)));
    }

    public Optional<TeamDTO> getById(int id) {
        Optional<Team> teamOptional = teamRepository.getById(id);
        return teamOptional.map(this::mapTeamToDTO);
    }


    public Optional<TeamDTO> update(int id, TeamDTO updatedTeamDTO) {
        Optional<Team> teamOptional = teamRepository.update(id, mapDTOToTeam(updatedTeamDTO));
        return teamOptional.map(this::mapTeamToDTO);
    }

    public void delete(int id) {
        teamRepository.delete(id);
    }

    private Team mapDTOToTeam(TeamDTO teamDTO) {
        return modelMapper.map(teamDTO, Team.class);
    }

    private TeamDTO mapTeamToDTO(Team team) {
        return modelMapper.map(team, TeamDTO.class);
    }
}


