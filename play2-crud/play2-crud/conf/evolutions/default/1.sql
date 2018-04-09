# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table stock (
  id                        bigint auto_increment not null,
  code                      varchar(255),
  name                      varchar(255),
  simple_name               varchar(255),
  version                   double,
  stocktotalnum             bigint,
  update_time               datetime not null,
  constraint pk_stock primary key (id))
;

create table time_line (
  id                        bigint auto_increment not null,
  stk_code                  varchar(255),
  stk_name                  varchar(255),
  date                      varchar(255),
  time                      varchar(255),
  price                     double,
  volume                    double,
  amount                    double,
  constraint pk_time_line primary key (id))
;

create table time_line (
  id                        bigint auto_increment not null,
  stk_code                  varchar(255),
  stk_name                  varchar(255),
  date                      varchar(255),
  time                      varchar(255),
  price                     double,
  volume                    double,
  amount                    double,
  money1                    double,
  money2                    double,
  money3                    double,
  money4                    double,
  constraint pk_time_line primary key (id))
;




# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table stock;

drop table time_line;

drop table time_line;

SET FOREIGN_KEY_CHECKS=1;

