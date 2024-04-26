insert into users (id, username, password, email) VALUES
(1,'username','password','email1');

insert into teams(id, name) VALUES
(1,'team');

insert into projects(id, title, description, start_date, status, team_id, manager_id) VALUES
(1,'title','description',localtimestamp,'status',1,1);

insert into tasks(id, title, status, priority, start_date, due_date, reporter_id, assignee_id, category, label, description, project_id) VALUES
(1,'title','status','priority',localtimestamp,localtimestamp,1,1,'category','label','description',1);

insert into comments(id, text, add_time, user_id, task_id) values
(1,'text',localtimestamp,1,1);