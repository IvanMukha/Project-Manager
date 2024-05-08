insert into users(id, username, password, email) VALUES
    (1,'username','{bcrypt}$2a$10$T5mipI5Vzbf4w0eEvdWkre/uh5j.lzkGegUfdWcYFjohhmdhk8ce2','email@email.com');
insert into roles(id, name) VALUES
    (1,'USER');
insert into users_roles(user_id, role_id) VALUES
(1,1);