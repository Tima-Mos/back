CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

drop table if exists animal;
drop table if exists form;
create sequence form_seq start with 1 increment by 50;
create table animal (id uuid not null, name varchar(255) not null, animal_form varchar(255), primary key (id));
create table form (name varchar(255) not null, primary key (name));
alter table if exists animal add constraint FKp4cuef1g2h0mtyq8s4rsk8uso foreign key (animal_form) references form;

