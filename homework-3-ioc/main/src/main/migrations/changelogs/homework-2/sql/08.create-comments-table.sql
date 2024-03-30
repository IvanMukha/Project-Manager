CREATE TABLE comments (
                       id INT PRIMARY KEY,
                       text TEXT NOT NULL,
                       add_time timestamp NOT NULL ,
                       user_id INT NOT NULL,
                       task_id INT NOT NULL
);
