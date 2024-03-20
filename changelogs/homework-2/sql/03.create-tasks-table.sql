CREATE TABLE tasks (
                       id INT PRIMARY KEY,
                       title VARCHAR(50) NOT NULL,
                       status VARCHAR(50) NOT NULL,
                       priority VARCHAR(50) NOT NULL,
                       start_date TIMESTAMP NOT NULL,
                       due_date TIMESTAMP NOT NULL,
                       reporter_id INT NOT NULL,
                       assignee_id INT,
                       category VARCHAR(30),
                       label VARCHAR(30),
                       description TEXT,
                       project_id INT NOT NULL
);

