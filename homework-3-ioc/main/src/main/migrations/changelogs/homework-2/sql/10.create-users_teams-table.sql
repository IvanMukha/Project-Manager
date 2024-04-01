CREATE TABLE users_teams
(
    user_id INT,
    team_id INT,
    UNIQUE (user_id, team_id)
);