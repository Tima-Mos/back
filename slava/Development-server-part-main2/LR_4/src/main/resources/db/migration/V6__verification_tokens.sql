create table verification_tokens (
                                     id uuid not null, confirmed boolean not null,
                                     expiry_date timestamp(6), token varchar(255),
                                     user_id uuid not null, primary key (id)
);
alter table if exists verification_tokens add constraint FK54y8mqsnq1rtyf581sfmrbp4f foreign key (user_id) references users;