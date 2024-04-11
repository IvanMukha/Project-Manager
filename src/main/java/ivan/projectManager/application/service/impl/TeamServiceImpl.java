package ivan.projectManager.application.service.impl;

import ivan.projectManager.application.dto.TeamDTO;
import ivan.projectManager.application.model.Team;
import ivan.projectManager.application.repository.TeamRepository;
import ivan.projectManager.application.service.TeamService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeamServiceImpl implements TeamService {
    private final ModelMapper modelMapper;
    private final TeamRepository teamRepositoryImpl;

    @Autowired
    public TeamServiceImpl(ModelMapper modelMapper, TeamRepository teamRepositoryImpl) {
        this.modelMapper = modelMapper;
        this.teamRepositoryImpl = teamRepositoryImpl;
    }

    public List<TeamDTO> getAll() {
        return teamRepositoryImpl.getAll().stream().map(this::mapTeamToDTO).collect(Collectors.toList());
    }

    public TeamDTO save(TeamDTO teamDTO) {
        return mapTeamToDTO(teamRepositoryImpl.save(mapDTOToTeam(teamDTO)));
    }

    public Optional<TeamDTO> getById(int id) {
        Optional<Team> teamOptional = teamRepositoryImpl.getById(id);
        return teamOptional.map(this::mapTeamToDTO);
    }


    public Optional<TeamDTO> update(int id, TeamDTO updatedTeamDTO) {
        Optional<Team> teamOptional = teamRepositoryImpl.update(id, mapDTOToTeam(updatedTeamDTO));
        return teamOptional.map(this::mapTeamToDTO);
    }

    public void delete(int id) {
        teamRepositoryImpl.delete(id);
    }

    private Team mapDTOToTeam(TeamDTO teamDTO) {
        return modelMapper.map(teamDTO, Team.class);
    }

    private TeamDTO mapTeamToDTO(Team team) {
        return modelMapper.map(team, TeamDTO.class);
    }
}


