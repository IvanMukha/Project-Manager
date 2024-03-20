ALTER TABLE users
    ADD CONSTRAINT fk_user_teams
        FOREIGN KEY (team_id) REFERENCES teams(id);

ALTER TABLE users
    ADD CONSTRAINT fk_user_id_roles
        FOREIGN KEY (role_id) REFERENCES roles(id);

ALTER TABLE tasks
    ADD CONSTRAINT fk_reporter_id_tasks
        FOREIGN KEY (reporter_id) REFERENCES users(id);

ALTER TABLE tasks
    ADD CONSTRAINT fk_assignee_id_tasks
        FOREIGN KEY (assignee_id) REFERENCES users(id);

ALTER TABLE tasks
    ADD CONSTRAINT fk_project_id_tasks
        FOREIGN KEY (project_id) REFERENCES projects(id);

ALTER TABLE reports
    ADD CONSTRAINT fk_task_id_reports
        FOREIGN KEY (task_id) REFERENCES tasks(id);

ALTER TABLE reports
    ADD CONSTRAINT fk_user_id_reports
        FOREIGN KEY (user_id) REFERENCES users(id);

ALTER TABLE attachments
    ADD CONSTRAINT fk_task_id_attachments
        FOREIGN KEY (task_id) REFERENCES tasks(id);

ALTER TABLE projects
    ADD CONSTRAINT fk_team_id_projects
        FOREIGN KEY (team_id) REFERENCES teams(id);

ALTER TABLE projects
    ADD CONSTRAINT fk_manager_id_projects
        FOREIGN KEY (manager_id) REFERENCES users(id);

ALTER TABLE comments
    ADD CONSTRAINT fk_user_id_projects
        FOREIGN KEY (user_id) REFERENCES users(id);

ALTER TABLE comments
    ADD CONSTRAINT fk_task_id_projects
        FOREIGN KEY (task_id) REFERENCES tasks(id);

ALTER TABLE user_details
    ADD CONSTRAINT fk_user_id_details
        FOREIGN KEY (user_id) REFERENCES users(id);


