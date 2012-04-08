# --- First database schema

# --- !Ups

set ignorecase true;

create table user (
  id                        bigint not null,
  email                     varchar(255) not null,
  name                      varchar(255) not null,
  password                  varchar(255) not null,
  constraint pk_user primary key (id))
;

create table make (
  id                        bigint not null,
  name                      varchar(255) not null,
  constraint pk_make primary key (id))
;

create table engine (
  id                        bigint not null,
  aspiration                varchar(127),
  position                  varchar(127),
  cc                        int,
  cylinders                 int,
  type                      varchar(127),
  valves                    int,
  constraint pk_engine primary key (id))
;

create table performance (
  id                        bigint not null,
  power                     int,
  power_rpm                 int,
  torque                    int,
  torque_rpm                int,
  constraint pk_performance primary key (id))
;

create table economy (
  id                        bigint not null,
  highway                   int,
  mixed                     int,
  city                      int,
  tank_size                 int,
  constraint pk_economy primary key(id))
;

create table drivetrain (
  id                        bigint not null,
  drivetype                 varchar(127),
  transmission              varchar(127),
  constraint pk_drivetrain primary key(id))
;

create table dimensions (
  id                       bigint not null,
  weight                   int,
  length                   int,
  width                    int,
  height                   int,
  wheelbase                int,
  constraint pk_dimensions primary key (id))
;

create table model (
  id                        bigint not null,
  name                      varchar(255) not null,
  year                      timestamp,
  trim                      varchar(127),
  seats                     int,
  doors                     int,
  body                      varchar(127),
  make_id                   bigint,
  engine_id                 bigint,
  drivetrain_id             bigint,
  dimensions_id             bigint,
  economy_id                bigint,
  performance_id            bigint,
  constraint pk_model primary key (id))
;

create table image (
  id                        bigint not null,
  data                      blob,
  constraint pk_image primary key (id))
;

create table sale (
  id                        bigint not null,
  user_id                   bigint,
  model_id                  bigint,
  image_id					bigint,
  year                      timestamp,
  price						int,
  mileage					int,
  constraint pk_sale primary key (id))
;

create table sale_comment (
  id                        bigint not null,
  user_id                   bigint,
  sale_id                   bigint,
  text                      varchar,
  accepted                  boolean,
  constraint pk_sale_comment primary key (id))
;


create sequence user_seq start with 1000;
create sequence make_seq start with 1000;
create sequence engine_seq start with 1000;
create sequence model_seq start with 1000;
create sequence sale_seq start with 1000;
create sequence image_seq start with 1000;
create sequence sale_comment_seq start with 1000;

alter table model add constraint fk_model_make_1 foreign key (make_id) references make (id) on delete cascade on update cascade;
alter table model add constraint fk_model_engine_1 foreign key (engine_id) references engine (id) on delete cascade on update cascade;
alter table model add constraint fk_model_drivetrain_1 foreign key (drivetrain_id) references drivetrain (id) on delete cascade on update cascade;
alter table model add constraint fk_model_dimensions_1 foreign key (dimensions_id) references dimensions (id) on delete cascade on update cascade;
alter table model add constraint fk_model_economy_1 foreign key (economy_id) references economy (id) on delete cascade on update cascade;
alter table model add constraint fk_model_performance_1 foreign key (performance_id) references performance (id) on delete cascade on update cascade;

alter table sale add constraint fk_sale_user_1 foreign key (user_id) references user (id) on delete cascade on update cascade;
alter table sale add constraint fk_sale_model_1 foreign key (model_id) references model (id) on delete cascade on update cascade;
alter table sale add constraint fk_sale_image_1 foreign key (image_id) references image (id) on delete cascade on update cascade;

alter table sale_comment add constraint fk_sale_comment_sale foreign key (sale_id) references sale (id) on delete cascade on update cascade;
alter table sale_comment add constraint fk_sale_comment_user foreign key (user_id) references user (id) on delete cascade on update cascade;


create index ix_model_make_1 on model (make_id);

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;
drop table if exists make;
drop table if exists engine;
drop table if exists performance;
drop table if exists economy;
drop table if exists drivetrain;
drop table if exists dimensions;	
drop table if exists model;
drop table if exists image;
drop table if exists sale;
drop table if exists user;
drop table if exists sale_comment;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists make_seq;
drop sequence if exists aspiration_seq;
drop sequence if exists engine_seq;
drop sequence if exists model_seq;
drop sequence if exists image_seq;
drop sequence if exists sale_seq;
drop sequence if exists sale_comment_seq;
drop sequence if exists user_seq;
