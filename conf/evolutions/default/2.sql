# --- Sample dataset

# --- !Ups


insert into user (id, email, name, password) values (1, 'bob@gmail.com', 'Bob Sacamano', 'admin');
insert into user (id, email, name, password) values (2, 'john@gmail.com', 'John Sacamano', 'admin');

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


insert into image (id ,data) values(1, FILE_READ('conf/evolutions/default/images/DSC_0015.JPG'));
insert into image (id ,data) values(2, FILE_READ('conf/evolutions/default/images/DSC_0015.JPG'));
insert into image (id ,data) values(3, FILE_READ('conf/evolutions/default/images/DSC_0015.JPG'));
insert into image (id ,data) values(4, FILE_READ('conf/evolutions/default/images/DSC_0015.JPG'));
insert into image (id ,data) values(5, FILE_READ('conf/evolutions/default/images/DSC_0015.JPG'));
insert into image (id ,data) values(6, FILE_READ('conf/evolutions/default/images/DSC_0015.JPG'));

insert into sales (id,user_id,model_id, image_id, year, price, mileage) values ( 1, 1, 1, 1, '2002-01-02', 10020, 202239);
insert into sales (id,user_id,model_id, image_id, year, price, mileage) values ( 2, 1, 2, 2, '2002-03-02', 12220, 129999);
insert into sales (id,user_id,model_id, image_id, year, price, mileage) values ( 3, 1, 2, 3, '2008-01-04', 10020, 38888);
insert into sales (id,user_id,model_id, image_id, year, price, mileage) values ( 4, 2, 3, 4, '2012-01-02', 12220, 93999);
insert into sales (id,user_id,model_id, image_id, year, price, mileage) values ( 5, 2, 3, 5, '2003-01-02', 10020, 45449);
insert into sales (id,user_id,model_id, image_id, year, price, mileage) values ( 6, 1, 3, 6, '2007-01-02', 12220, 98000);


# --- !Downs

delete from user;
delete from aspiration;
delete from model;
delete from make;
delete from sales;
delete from image;
