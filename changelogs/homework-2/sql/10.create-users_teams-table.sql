CREATE TABLE users_teams (
                             user_id INT,
                             team_id INT,
                             PRIMARY KEY (user_id, team_id),
                             FOREIGN KEY (user_id) REFERENCES users(id),
                             FOREIGN KEY (team_id) REFERENCES teams(id)
);