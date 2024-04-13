    package com.ivan.projectManager.repository.repositoryMapper;

    import com.ivan.projectManager.model.Team;
    import org.springframework.stereotype.Component;

    import java.sql.ResultSet;
    import java.sql.SQLException;
    import java.util.ArrayList;
    import java.util.List;
    @Component
    public class TeamRowMapper implements RowMapper<Team>{
        public List<Team> mapAll(ResultSet resultSet) {
            List<Team> teams = new ArrayList<>();
            try {
                while (resultSet.next()) {
                    Team team =map(resultSet);
                    teams.add(team);
                }
            } catch (SQLException e) {
                throw new RuntimeException("Failed to map teams", e);
            }
            return teams;
        }

        public Team map(ResultSet resultSet) {
            try {
                if (resultSet.next()) {
                    Team team = new Team();
                    team.setId(resultSet.getInt("id"));
                    team.setName(resultSet.getString("name"));
                    return team;
                }
            } catch (SQLException e) {
                throw new RuntimeException("Failed to map team", e);
            }
            return null;
        }
    }
