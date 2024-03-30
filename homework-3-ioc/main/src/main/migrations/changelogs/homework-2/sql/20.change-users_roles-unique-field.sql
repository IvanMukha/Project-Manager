DO '
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = ''users_roles'') THEN
        CREATE TABLE users_roles (
                                     user_id INT,
                                     role_id INT,
                                     CONSTRAINT users_teams_pk PRIMARY KEY (user_id, role_id)
        );
    END IF;
END ';

DO '
BEGIN
    IF NOT EXISTS (
        SELECT constraint_name
        FROM information_schema.table_constraints
        WHERE table_name = ''users_roles'' AND constraint_name = ''users_roles_pk''
    ) THEN
        ALTER TABLE users_roles
            ADD CONSTRAINT users_roles_pk PRIMARY KEY (user_id, role_id);
    END IF;
END ';
