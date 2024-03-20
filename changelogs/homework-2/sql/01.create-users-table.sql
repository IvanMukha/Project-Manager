
CREATE TABLE users (
                       id INT PRIMARY KEY,
                       username VARCHAR(30) UNIQUE NOT NULL,
                       password VARCHAR(30) NOT NULL,
                       email VARCHAR(50) NOT NULL,
                       role_id INT NOT NULL,
                       team_id INT NOT NULL
);

