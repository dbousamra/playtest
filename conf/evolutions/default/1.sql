# --- First database schema

# --- !Ups

set ignorecase true;

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

create sequence make_seq start with 1000;
create sequence aspiration_seq start with 1000;
create sequence model_seq start with 1000;

alter table model add constraint fk_model_make_1 foreign key (make_id) references make (id) on delete restrict on update restrict;
alter table model add constraint fk_model_aspiration_1 foreign key (aspiration_id) references aspiration (id) on delete restrict on update restrict;

create index ix_model_make_1 on model (make_id);
create index ix_model_aspiration_1 on model (aspiration_id);


# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists make;
drop table if exists aspiration;
drop table if exists model;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists make_seq;
drop sequence if exists aspiration_seq;
drop sequence if exists model_seq;
