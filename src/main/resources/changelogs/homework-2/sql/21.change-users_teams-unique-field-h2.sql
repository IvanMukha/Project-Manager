CREATE TABLE IF NOT EXISTS users_teams (
                                           user_id INT NOT NULL,
                                           team_id INT NOT NULL,
                                           PRIMARY KEY (user_id, team_id)
);

ALTER TABLE users_teams
    ALTER COLUMN user_id SET NOT NULL;

ALTER TABLE users_teams
 ALTER COLUMN team_id SET NOT NULL;

ALTER TABLE users_teams
    ADD CONSTRAINT IF NOT EXISTS users_teams_pk PRIMARY KEY (user_id, team_id);

