insert into users(id, username, password, email)
VALUES (1, 'user1', 'password1', 'email1@gmail.com'),
       (2, 'user2', 'password2', 'email2@gmail.com');
insert into teams(id, name)
VALUES (1, 'team1'),
       (2, 'team2');
insert into roles(id, name)
VALUES (1, 'role1'),
       (2, 'role2');
insert into users_roles(user_id, role_id)
VALUES (1, 1),
       (2, 2);
insert into users_teams(user_id, team_id)
VALUES (1, 1),
       (2, 2);