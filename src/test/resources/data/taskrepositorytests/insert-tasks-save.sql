INSERT INTO users (id, username, password, email)
VALUES (1, 'john_doe', 'password123', 'john@example.com'),
       (2, 'john_doe2', 'password123', 'john2@example.com');

INSERT INTO teams (id, name)
VALUES (1, 'Development Team');

INSERT INTO projects (id, title, description, start_date, status, team_id, manager_id)
VALUES (1, 'Project ABC', 'Description of Project ABC', '2024-04-17 09:00:00', 'Active', 1, 1);