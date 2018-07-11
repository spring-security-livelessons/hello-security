insert into user (id, name, email, password) values (1, 'Rob Winch', 'rob@example.com', 'password');
insert into user (id, name, email, password) values (2, 'Josh Long', 'josh@example.com', 'password');

insert into message (id, text, to_id, from_id) values (100, 'This message is for Rob', 1, 2);
insert into message (id, text, to_id, from_id) values (101, 'This message is also for Rob', 1, 2);

insert into message (id, text, to_id, from_id) values (200, 'This message is for Josh', 2, 1);
insert into message (id, text, to_id, from_id) values (201, 'This message is also for Josh', 2, 1);