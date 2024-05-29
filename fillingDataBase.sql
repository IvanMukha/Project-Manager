-- ?????????? ??????? users
INSERT INTO users (id, username, password, email) VALUES
 (2, 'user2', '$2a$10$XqimPNaLEJ2ZzP1kfq.dN.4BgY.y1Z6ebhPBR2Qo4oRP7uYfrfBbW', 'user2@example.com'),
 (3, 'user3', '$2a$10$TkH8WY8EB5eWdRZHsKzRMuM8Gh8eP4qfF6PpA2ZTNMElde8CmSl/y', 'user3@example.com'),
 (4, 'user4', '$2a$10$2AEDmN7/MPgZUVZrmBm5Gu8KLHgQCLo4ecU.AuoozQ0/xhVcXdUZC', 'user4@example.com'),
 (5, 'user5', '$2a$10$dImvjRc62ftkh8YfSX35/ON6s4xc39DH7WZ1yBVFnji3xOt.q7zOa', 'user5@example.com'),
 (6, 'user6', '$2a$10$7k9FCcRfx8EzEsDyfPHvF.a25NMTXoy4ivBsG7L8emzCNR7a2l9pm', 'user6@example.com'),
 (7, 'user7', '$2a$10$YakfQr0GVZzxq0mpwIwzLu24wO4rlWNRmvWVQVoY45hnFL1KoU9Au', 'user7@example.com'),
 (8, 'user8', '$2a$10$maBzWy6h.gZzBcizLFXClOhY9EYdDWV6t8lnrUNNyKsF/CyBCq5ZC', 'user8@example.com'),
 (9, 'user9', '$2a$10$Mi5U4z3BYJGfGzV2oUx3H.8vcIKFSBXCCbfnS3mSf.97lfLpqGGh6', 'user9@example.com'),
 (10, 'user10', '$2a$10$RRd5yB/cf21s6f6A7sz78uF3zxEqO9v9G3iN0iIXw.GsM7h5J1quC', 'user10@example.com');

--?????????? ??????? Roles
insert into roles(id, name) VALUES
(2,'USER'),
(3,'ADMIN');

--?????????? ??????? users_roles
insert into users_roles(user_id, role_id) VALUES
(2,3),
(3,2),
(4,2),
(5,2),
(6,2),
(7,2),
(8,2),
(9,2),
(10,2);

--?????????? ??????? teams
insert into teams(id, name) VALUES
(2,'team2'),
(3,'team3'),
(4,'team4'),
(5,'team5'),
(6,'team6');

--?????????? ??????? users_teams
insert into users_teams(user_id, team_id) VALUES
(2,2),
(3,2),
(4,3),
(5,3),
(6,4),
(7,4),
(8,5),
(9,5),
(10,6);

-- ?????????? ??????? user_details
INSERT INTO user_details (user_id, name, surname, phone, work_phone, work_adress, department) VALUES
(2, 'Jane', 'Doe', '+1234567891', '+9876543211', '456 Elm St, City, Country', 'HR Department'),
(3, 'Alice', 'Smith', '+1234567892', '+9876543212', '789 Oak St, City, Country', 'Finance Department'),
(4, 'Bob', 'Johnson', '+1234567893', '+9876543213', '101 Pine St, City, Country', 'Marketing Department'),
(5, 'Emily', 'Brown', '+1234567894', '+9876543214', '222 Cedar St, City, Country', 'Sales Department'),
(6, 'Michael', 'Wilson', '+1234567895', '+9876543215', '333 Maple St, City, Country', 'Operations Department'),
(7, 'Sophia', 'Martinez', '+1234567896', '+9876543216', '444 Birch St, City, Country', 'Customer Service Department'),
(8, 'Jacob', 'Anderson', '+1234567897', '+9876543217', '555 Walnut St, City, Country', 'Research Department'),
(9, 'Olivia', 'Taylor', '+1234567898', '+9876543218', '666 Pine St, City, Country', 'Development Department'),
(10, 'William', 'Thomas', '+1234567899', '+9876543219', '777 Oak St, City, Country', 'Design Department');

-- ?????????? ??????? projects
INSERT INTO projects (id, title, description, start_date, status, team_id, manager_id) VALUES
(2, 'Project 2', 'Description for Project 2', '2024-05-29', 'In Progress', 2, 2),
(3, 'Project 3', 'Description for Project 3', '2024-05-29', 'In Progress', 3, 3),
(4, 'Project 4', 'Description for Project 4', '2024-05-29', 'In Progress', 4, 4),
(5, 'Project 5', 'Description for Project 5', '2024-05-29', 'In Progress', 5, 5),
(6, 'Project 6', 'Description for Project 6', '2024-05-29', 'In Progress', 2, 6),
(7, 'Project 7', 'Description for Project 7', '2024-05-29', 'In Progress', 2, 7),
(8, 'Project 8', 'Description for Project 8', '2024-05-29', 'In Progress', 3, 8),
(9, 'Project 9', 'Description for Project 9', '2024-05-29', 'In Progress', 4, 9),
(10, 'Project 10', 'Description for Project 10', '2024-05-29', 'In Progress', 5, 10);

--?????????? ??????? tasks
insert into  tasks (id, title, status, priority, start_date, due_date, reporter_id, assignee_id, category, label,
                    description, project_id) VALUES
(2, 'Task 2', 'Completed', 'High', '2024-04-17 10:00:00', '2024-01-20 17:00:00', 2, 3, 'Development', 'Bug',
        'Description of the task', 2),
(3, 'Task 3', 'Completed', 'Low', '2024-04-17 10:00:00', '2024-01-20 17:00:00', 2, 3, 'Development', 'Bug',
        'Description of the task', 2),
(4, 'Task 4', 'Completed', 'Low', '2024-04-17 10:00:00', '2024-01-21 17:00:00', 2, 3, 'Development', 'Bug',
        'Description of the task', 2),
(5, 'Task 5', 'Completed', 'Medium', '2024-04-17 10:00:00', '2024-01-21 17:00:00', 2, 3, 'Development', 'Bug',
        'Description of the task', 2),
(6, 'Task 6', 'Completed', 'Medium', '2024-04-17 10:00:00', '2024-01-22 17:00:00', 4,5, 'Development', 'Bug',
        'Description of the task', 2),
(7, 'Task 7', 'Completed', 'High', '2024-04-17 10:00:00', '2024-01-22 17:00:00', 4, 5, 'Development', 'Bug',
        'Description of the task', 2),
(8, 'Task 8', 'In Progress', 'High', '2024-04-17 10:00:00', '2024-01-23 17:00:00', 4,5, 'Development', 'Bug',
        'Description of the task', 2),
(9, 'Task 9', 'In Progress', 'High', '2024-04-17 10:00:00', '2024-01-24 17:00:00', 4, 5, 'Development', 'Bug',
    'Description of the task', 2),
(10, 'Task 10', 'In Progress', 'High', '2024-04-17 10:00:00', '2024-01-24 17:00:00', 4, 5, 'Development', 'Bug',
        'Description of the task', 2);

--?????????? ??????? reports
INSERT INTO reports (id, title, text, create_at, user_id, task_id)
VALUES
       (2, 'Report 2', 'Text of report 2', '2024-05-29 18:00:00', 2, 2),
       (3, 'Report 3', 'Text of report 3', '2024-05-29 18:00:00', 3, 3),
       (4, 'Report 4', 'Text of report 4', '2024-05-29 18:00:00', 4, 4),
       (5, 'Report 5', 'Text of report 5', '2024-05-29 18:00:00', 5, 5),
       (6, 'Report 6', 'Text of report 6', '2024-05-29 18:00:00', 6, 6),
       (7, 'Report 7', 'Text of report 7', '2024-05-29 18:00:00', 7, 7),
       (8, 'Report 8', 'Text of report 8', '2024-05-29 18:00:00', 8, 8),
       (9, 'Report 9', 'Text of report 9', '2024-05-29 18:00:00', 9, 9),
       (10, 'Report 10', 'Text of report 10', '2024-05-29 18:00:00', 10, 10);

--?????????? ??????? comments
INSERT INTO comments (id, text, add_time, user_id, task_id)
VALUES
       (2, 'Comment 2 for task 1', '2024-05-29 18:00:00', 2, 2),
       (3, 'Comment 1 for task 2', '2024-05-29 18:00:00', 3, 2),
       (4, 'Comment 2 for task 2', '2024-05-29 18:00:00', 4, 2),
       (5, 'Comment 1 for task 3', '2024-05-29 18:00:00', 5, 3),
       (6, 'Comment 2 for task 3', '2024-05-29 18:00:00', 6, 3),
       (7, 'Comment 1 for task 4', '2024-05-29 18:00:00', 7, 4),
       (8, 'Comment 2 for task 4', '2024-05-29 18:00:00', 8, 4),
       (9, 'Comment 1 for task 5', '2024-05-29 18:00:00', 9, 5),
       (10, 'Comment 2 for task 5', '2024-05-29 18:00:00', 10, 5);

--?????????? ??????? attachments
INSERT INTO attachments (id, title, path, task_id)
VALUES
    (2, 'Attachment 2 for Task 2', '/path/to/attachment2', 2),
    (3, 'Attachment 1 for Task 2', '/path/to/attachment3', 2),
    (4, 'Attachment 2 for Task 2', '/path/to/attachment4', 2),
    (5, 'Attachment 1 for Task 3', '/path/to/attachment5', 3),
    (6, 'Attachment 2 for Task 3', '/path/to/attachment6', 3),
    (7, 'Attachment 1 for Task 4', '/path/to/attachment7', 4),
    (8, 'Attachment 2 for Task 4', '/path/to/attachment8', 4),
    (9, 'Attachment 1 for Task 5', '/path/to/attachment9', 5),
    (10, 'Attachment 2 for Task 5', '/path/to/attachment10', 5);

