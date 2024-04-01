CREATE TABLE users
(
    id       INT PRIMARY KEY,
    username VARCHAR UNIQUE NOT NULL,
    password VARCHAR        NOT NULL,
    email    VARCHAR        NOT NULL
);

