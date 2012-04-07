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

create table aspiration (
  id                        bigint not null,
  type                      char(12) not null,
  constraint pk_aspiration primary key (id))
;

create table model (
  id                        bigint not null,
  name                      varchar(255) not null,
  introduced                timestamp,
  discontinued              timestamp,
  make_id                   bigint,
  aspiration_id				bigint,
  constraint pk_model primary key (id))
;

create table image (
  id                        bigint not null,
  data                 		blob,
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
create sequence aspiration_seq start with 1000;
create sequence model_seq start with 1000;
create sequence sale_seq start with 1000;
create sequence image_seq start with 1000;
create sequence sale_comment_seq start with 1000;

alter table model add constraint fk_model_make_1 foreign key (make_id) references make (id) on delete cascade on update cascade;
alter table model add constraint fk_model_aspiration_1 foreign key (aspiration_id) references aspiration (id) on delete cascade on update cascade;
alter table sale add constraint fk_sale_user_1 foreign key (user_id) references user (id) on delete cascade on update cascade;
alter table sale add constraint fk_sale_model_1 foreign key (model_id) references model (id) on delete cascade on update cascade;
alter table sale add constraint fk_sale_image_1 foreign key (image_id) references image (id) on delete cascade on update cascade;
alter table sale_comment add constraint fk_sale_comment_sale foreign key (sale_id) references sale (id) on delete cascade on update cascade;
alter table sale_comment add constraint fk_sale_comment_user foreign key (user_id) references user (id) on delete cascade on update cascade;


create index ix_model_make_1 on model (make_id);
create index ix_model_aspiration_1 on model (aspiration_id);


# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;
drop table if exists make;
drop table if exists aspiration;
drop table if exists model;
drop table if exists image;
drop table if exists sale;
drop table if exists user;
drop table if exists sale_comment;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists make_seq;
drop sequence if exists aspiration_seq;
drop sequence if exists model_seq;
drop sequence if exists image_seq;
drop sequence if exists sale_seq;
drop sequence if exists sale_comment_seq;
drop sequence if exists user_seq;
