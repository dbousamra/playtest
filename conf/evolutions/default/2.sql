# --- Sample dataset

# --- !Ups

insert into user (id, email, name, password) values (1, 'bob@gmail.com', 'Bob Sacamano', 'admin');
insert into user (id, email, name, password) values (2, 'john@gmail.com', 'John Sacamano', 'admin');

insert into make (id,name) values (1, 'Renault');
insert into make (id,name) values (2, 'Nissan');
insert into make (id,name) values (3, 'Porsche');
insert into make (id,name) values (4, 'Subaru');

insert into engine (id, aspiration, position, cc, cylinders, type, valves) values (1, 'Supercharged', 'Front', '1998', 4, 'Inline', 16);

insert into drivetrain(id, drivetype, transmission) values (1, 'AWD', 'Manual');

insert into performance(id, power, power_rpm, torque, torque_rpm) values (1, 182, 6500, 200, 5400);

insert into economy(id, highway, mixed, city, tank_size) values (1, 22, 27, 65, 65);

insert into dimensions(id, weight, length, width, height, wheelbase) values (1, 2220, 4400, 2200, 2222, 1922);

insert into model (id, name, year, trim, seats, doors, body, make_id, engine_id, drivetrain_id, dimensions_id, economy_id, performance_id) values (1, 'Clio', '2004-01-01', 'Sport 182', 5, 3, 'Hatchback', 1, 1, 1, 1, 1, 1);

insert into image (id ,data) values(1, FILE_READ('conf/evolutions/default/images/DSC_0015.JPG'));
insert into image (id ,data) values(2, FILE_READ('conf/evolutions/default/images/DSC_0015.JPG'));
insert into image (id ,data) values(3, FILE_READ('conf/evolutions/default/images/DSC_0015.JPG'));
insert into image (id ,data) values(4, FILE_READ('conf/evolutions/default/images/DSC_0015.JPG'));
insert into image (id ,data) values(5, FILE_READ('conf/evolutions/default/images/DSC_0015.JPG'));
insert into image (id ,data) values(6, FILE_READ('conf/evolutions/default/images/DSC_0015.JPG'));

insert into sale (id,user_id,model_id, image_id, year, price, mileage) values ( 1, 1, 1, 1, '2002-01-02', 10020, 202239);

insert into sale_comment (id, user_id, sale_id, text, accepted) values (1, 1, 1, 'Test comment', true);

# --- !Downs

delete from user;
delete from drivetrain;
delete from engine;
delete from performance;
delete from dimensions;
delete from economy;
delete from model;
delete from make;
delete from sale;
delete from image;
