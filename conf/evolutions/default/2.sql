# --- Sample dataset

# --- !Ups

insert into user (id, email, name, password) values (1, 'bob@gmail.com', 'Bob Sacamano', 'admin');
insert into user (id, email, name, password) values (2, 'john@gmail.com', 'John Sacamano', 'admin');

# --- !Downs

delete from user;
