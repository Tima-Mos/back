create table if not exists names (name varchar(255) not null, primary key (name));
create table if not exists money (id uuid not null, date varchar(255) not null, price integer not null, quantity integer not null, value integer not null, money_name varchar(255), primary key (id));
alter table money add constraint FK10e0iwb3ifkjcj5l195jtoe4x foreign key (money_name) references names;

