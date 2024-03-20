CREATE TABLE tasks (
                       id INT PRIMARY KEY,
                       title VARCHAR NOT NULL,
                       status VARCHAR NOT NULL,
                       priority VARCHAR NOT NULL,
                       start_date TIMESTAMP NOT NULL,
                       due_date TIMESTAMP NOT NULL,
                       reporter_id INT NOT NULL,
                       assignee_id INT,
                       category VARCHAR,
                       label VARCHAR,
                       description TEXT,
                       project_id INT NOT NULL
);

