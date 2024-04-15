CREATE TABLE users_roles
(
    user_id INT,
    role_id INT,
    UNIQUE (user_id, role_id)
);