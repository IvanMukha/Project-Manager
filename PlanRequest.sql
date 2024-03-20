
explain analyse
    select * from user_details
        inner join public.users u on u.id = user_details.user_id
             where u.id=5;

explain analyse
    select * from comments
        inner join public.users u on comments.user_id = u.id
             where u.id=5;

explain analyse
    select * from comments
        inner join public.tasks t on comments.task_id=t.id
             where t.id<20;

explain analyse
    select * from projects
        inner join public.teams t on projects.team_id = t.id
             where t.id=4;

explain analyse
    select * from projects
        inner join public.users u on projects.manager_id = u.id
             where u.id=4;

explain analyse
    select * from tasks
        inner join  public.users u on tasks.reporter_id = u.id
             where u.id=2;

explain analyse
    select * from tasks
        inner join public.users u on u.id = tasks.assignee_id
             where u.id>4 and u.id <10;

explain analyse
    select * from tasks
        inner join public.projects p on p.id = tasks.project_id
             where p.id=5;

explain analyse
    select * from reports
        inner join public.users u on u.id = reports.user_id
             where u.id=3;

explain analyse
    select * from reports
        inner join public.tasks t on t.id =reports.task_id
             where t.id=5;

explain analyse
    select * from attachments
        inner join public.tasks t on attachments.task_id = t.id
             where t.id=15;







