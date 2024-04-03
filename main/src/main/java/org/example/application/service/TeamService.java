package org.example.application.service;

import org.example.application.dto.TeamDTO;
import org.example.application.model.Team;
import org.example.application.repository.TeamRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeamService {
    private final ModelMapper modelMapper;
    private final TeamRepository teamRepository;

    @Autowired
    public TeamService(ModelMapper modelMapper, TeamRepository teamRepository) {
        this.modelMapper = modelMapper;
        this.teamRepository = teamRepository;
    }

    public List<TeamDTO> getAll() {
        return teamRepository.getAll().stream().map(this::mapTeamToDTO).collect(Collectors.toList());
    }

    public void save(TeamDTO teamDTO) {
        teamRepository.save(mapDTOToTeam(teamDTO));
    }

    public Optional<TeamDTO> getById(int id) {
        Optional<Team> teamOptional = teamRepository.getById(id);
        return teamOptional.map(this::mapTeamToDTO);
    }

    public void update(int id, TeamDTO updatedTeamDTO) {
        teamRepository.update(id, mapDTOToTeam(updatedTeamDTO));
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


