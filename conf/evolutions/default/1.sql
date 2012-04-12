# --- First database schema

# --- !Ups

set ignorecase true;

create table user (
  id bigint not null primary key auto_increment,
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

create table modelDetails (
  id                        bigint not null,
  position                  varchar(127),
  cc                        int,
  cylinders                 int,
  engine_type               varchar(127),
  valves                    int,
  
  drivetype                 varchar(127),
  transmission              varchar(127),
  
  weight                    int,
  length                    int,
  width                     int,
  height                    int,
  wheelbase                 int,
  
  highway                   int,
  mixed                     int,
  city                      int,
  tank_size                 int,
  
  power                     int,
  power_rpm                 int,
  torque                    int,
  torque_rpm                int,
  constraint pk_model_details primary key (id))
;

create table model (
  id                        bigint not null,
  
  makeId                    bigint,
  modelDetailsId			bigint,
  
  name                      varchar(255) not null,
  year                      timestamp,
  trim                      varchar(127),
  seats                     int,
  doors                     int,
  body                      varchar(127),
  
  constraint pk_model primary key (id))
;

create table image (
  id                        bigint not null,
  data                      blob,
  constraint pk_image primary key (id))
;

create table sale (
  id                        bigint not null,
  userId                   bigint,
  modelId                  bigint,
  imageId					bigint,
  year                      timestamp,
  price						int,
  mileage					int,
  constraint pk_sale primary key (id))
;

create table salecomment (
  id                        bigint not null,
  userId                   bigint,
  saleId                   bigint,
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
create sequence salecomment_seq start with 1000;

alter table model add constraint fk_model_make_1 foreign key (makeId) references make (id) on delete cascade on update cascade;
alter table model add constraint fk_model_model_details_1 foreign key (modelDetailsId) references modelDetails (id) on delete cascade on update cascade;

alter table sale add constraint fk_sale_user_1 foreign key (userId) references user (id) on delete cascade on update cascade;
alter table sale add constraint fk_sale_model_1 foreign key (modelId) references model (id) on delete cascade on update cascade;
alter table sale add constraint fk_sale_image_1 foreign key (imageId) references image (id) on delete cascade on update cascade;

alter table salecomment add constraint fk_sale_comment_sale foreign key (saleId) references sale (id) on delete cascade on update cascade;
alter table salecomment add constraint fk_sale_comment_user foreign key (userId) references user (id) on delete cascade on update cascade;


create index ix_model_make_1 on model (makeId);

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;
drop table if exists make;
drop table if exists modelDetails;
drop table if exists model;
drop table if exists image;
drop table if exists sale;
drop table if exists user;
drop table if exists salecomment;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists make_seq;
drop sequence if exists engine_seq;
drop sequence if exists model_seq;
drop sequence if exists image_seq;
drop sequence if exists sale_seq;
drop sequence if exists salecomment_seq;
drop sequence if exists user_seq;
