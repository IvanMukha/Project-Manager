package com.ivan.projectmanager.service.impl;

import com.ivan.projectmanager.dto.TeamDTO;
import com.ivan.projectmanager.dto.UserDTO;
import com.ivan.projectmanager.exeptions.CustomNotFoundException;
import com.ivan.projectmanager.model.Team;
import com.ivan.projectmanager.model.User;
import com.ivan.projectmanager.repository.TeamRepository;
import com.ivan.projectmanager.repository.UserRepository;
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
    private final UserRepository userRepository;

    @Autowired
    public TeamServiceImpl(ModelMapper modelMapper, TeamRepository teamRepository, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
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

    @Transactional
    public Optional<TeamDTO> addUserToTeam(Long teamId, UserDTO userDTO) {
        User user = userRepository.getById(userDTO.getId()).orElseThrow(() -> new CustomNotFoundException(userDTO.getId(), User.class));
        Team team = teamRepository.getById(teamId).orElseThrow(() -> new CustomNotFoundException(teamId, Team.class));
        if (team.getUsers().stream().noneMatch(u -> u.getId().equals(userDTO.getId()))) {
            team.getUsers().add(user);
        }
        Optional<Team> teamOptional = teamRepository.update(teamId, team);
        return teamOptional.map(this::mapTeamToDTO);

    }

    @Transactional
    public Optional<TeamDTO> removeUserFromTeam(Long teamId, UserDTO userDTO) {
        Team team = teamRepository.getById(teamId).stream().findFirst().orElseThrow(() -> new CustomNotFoundException(teamId, Team.class));
        team.getUsers().removeIf(t -> t.getId().equals(userDTO.getId()));
        Optional<Team> teamOptional = teamRepository.update(teamId, team);
        return teamOptional.map(this::mapTeamToDTO);
    }


    private Team mapDTOToTeam(TeamDTO teamDTO) {
        return modelMapper.map(teamDTO, Team.class);
    }

    private TeamDTO mapTeamToDTO(Team team) {
        return modelMapper.map(team, TeamDTO.class);
    }
}


