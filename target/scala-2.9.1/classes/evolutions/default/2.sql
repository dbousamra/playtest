# --- Sample dataset

# --- !Ups

insert into make (id,name) values (1, 'Renault');
insert into make (id,name) values (2, 'Nissan');
insert into make (id,name) values (3, 'Porsche');
insert into make (id,name) values (4, 'Subaru');

insert into aspiration (id, type) values ( 1, 'Turbocharged' );
insert into aspiration (id, type) values ( 2, 'N/A' );
insert into aspiration (id, type) values ( 3, 'Supercharged' );

insert into model (id,name,introduced,discontinued,aspiration_id,make_id) values ( 1,'Clio','1980-05-01','1984-04-01', 2, 1);

insert into model (id,name,introduced,discontinued,aspiration_id,make_id) values ( 2,'Skyline','1980-05-01','1984-04-01', 1, 2);
insert into model (id,name,introduced,discontinued,aspiration_id,make_id) values ( 3,'370Z','1980-05-01','1984-04-01', 2, 2);

insert into model (id,name,introduced,discontinued,aspiration_id,make_id) values ( 4,'Cayman','1980-05-01','1984-04-01', 2, 3);
insert into model (id,name,introduced,discontinued,aspiration_id,make_id) values ( 5,'911','1980-05-01','1984-04-01', 2, 3);

insert into model (id,name,introduced,discontinued,aspiration_id,make_id) values ( 6,'Impreza','1980-05-01','1984-04-01', 1, 4);
insert into model (id,name,introduced,discontinued,aspiration_id,make_id) values ( 7,'Legacy','1980-05-01','1984-04-01', 1, 4);

# --- !Downs

delete from aspiration;
delete from model;
delete from make;
