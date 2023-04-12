CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
create table roles (
    name varchar(255) not null, primary key (name));
create table tokens (
    user_id uuid not null, killed boolean not null, value varchar(255) not null, primary key (user_id));
create table users (
    id uuid not null, email varchar(255) not null, enabled boolean not null, first_name varchar(25) not null, last_name varchar(25) not null,
     password varchar(255) not null, role varchar(255), primary key (id));
alter table if exists tokens add constraint UK_52mttatqjhrk9pyfcbbk6e2dp unique (value);
alter table if exists users add constraint UK_6dotkott2kjsp8vw4d0m25fb7 unique (email);
alter table if exists users add constraint FK4c6vlshk8x83ifeoggi3exg3k foreign key (role) references roles;