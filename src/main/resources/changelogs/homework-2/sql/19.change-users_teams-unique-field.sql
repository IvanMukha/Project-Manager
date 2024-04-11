DO '
BEGIN
IF NOT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = ''users_teams'') THEN
    CREATE TABLE users_teams
        (user_id INT, team_id INT, CONSTRAINT users_teams_pk PRIMARY KEY (user_id, team_id));
    END IF;
END ';

DO '
BEGIN
IF NOT EXISTS (SELECT constraint_name
FROM information_schema.table_constraints
WHERE table_name = ''users_teams'' AND constraint_name = ''users_teams_pk'') THEN
    ALTER TABLE users_teams
        ADD CONSTRAINT users_teams_pk PRIMARY KEY (user_id, team_id);
    END IF;
END ';
