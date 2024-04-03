CREATE TABLE reports
(
    id        INT PRIMARY KEY,
    title     VARCHAR   NOT NULL,
    text      TEXT,
    create_at TIMESTAMP NOT NULL,
    user_id   INT       NOT NULL,
    task_id   INT       NOT NULL

);
