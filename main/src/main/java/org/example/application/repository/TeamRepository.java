package org.example.application.repository;

import org.example.application.model.Team;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TeamRepository {
    List<Team> teams = new ArrayList<>();

    public void save(Team team) {
        teams.add(team);
    }

    public List<Team> getAll() {
        return new ArrayList<>(teams);
    }

    public Optional<Team> getById(int id) {
        return teams.stream().filter(team -> team.getId() == id).findFirst();
    }

    public void update(int id, Team updatedTeam) {
        Optional<Team> optionalTeam = getById(id);
        optionalTeam.ifPresent(team -> team.setName(updatedTeam.getName()));
    }

    public void delete(int id) {
        teams.removeIf(team -> team.getId() == id);
    }
}
