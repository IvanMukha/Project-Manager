package com.ivan.projectmanager.service;

import com.ivan.projectmanager.dto.TeamDTO;
import com.ivan.projectmanager.model.Team;
import com.ivan.projectmanager.repository.TeamRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = TestServiceConfiguration.class)
public class TeamServiceImplTest {

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private TeamService teamService;

    @Test
    void testGetAllTeams() {
        Team team = new Team().setName("Team 1");
        Team team2 = new Team().setName("Team 2");
        when(teamRepository.getAll()).thenReturn(List.of(team, team2));
        List<TeamDTO> result = teamService.getAll();
        assertEquals(2, result.size());
        assertEquals(team.getName(), result.get(0).getName());
        assertEquals(team2.getName(), result.get(1).getName());
        verify(teamRepository).getAll();
    }

    @Test
    void testSaveTeam() {
        Team team = new Team().setName("Test Team");
        TeamDTO teamDTO = new TeamDTO().setName("Test Team");
        Mockito.when(teamRepository.save(team)).thenReturn(team);
        TeamDTO savedTeamDTO = teamService.save(teamDTO);
        assertNotNull(savedTeamDTO);
        assertEquals(team.getName(), savedTeamDTO.getName());
        verify(teamRepository).save(any());
    }

    @Test
    void testGetTeamById() {
        String name = "Test Team";
        Team team = new Team().setName(name);
        when(teamRepository.getById(1L)).thenReturn(Optional.of(team));
        Optional<TeamDTO> result = teamService.getById(1L);
        assertTrue(result.isPresent());
        assertEquals(name, result.get().getName());
        verify(teamRepository).getById(1L);
    }

    @Test
    void testDeleteTeam() {
        Long id = 1L;
        teamService.delete(id);
        verify(teamRepository).delete(id);
    }
}
