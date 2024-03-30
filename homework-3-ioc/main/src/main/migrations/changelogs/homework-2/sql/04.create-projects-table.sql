CREATE TABLE projects (
                          id INT PRIMARY KEY,
                          title VARCHAR NOT NULL,
                          description TEXT ,
                          start_date TIMESTAMP NOT NULL,
                          status VARCHAR NOT NULL,
                          team_id INT,
                          manager_id INT

);
