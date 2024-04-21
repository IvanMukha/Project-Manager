CREATE TABLE IF NOT EXISTS users_roles (
                                           user_id INT NOT NULL,
                                           role_id INT NOT NULL,
                                           PRIMARY KEY (user_id, role_id)
);

ALTER TABLE users_roles
    ALTER COLUMN user_id SET NOT NULL;

ALTER TABLE users_roles
    ALTER COLUMN role_id SET NOT NULL;

ALTER TABLE users_roles
    ADD CONSTRAINT IF NOT EXISTS users_roles_pk PRIMARY KEY (user_id, role_id);

