CREATE TABLE projects (
                          id INT PRIMARY KEY,
                          title VARCHAR(50) NOT NULL,
                          description TEXT ,
                          start_date TIMESTAMP NOT NULL,
                          status VARCHAR(30) NOT NULL,
                          team_id INT,
                          manager_id INT

);
