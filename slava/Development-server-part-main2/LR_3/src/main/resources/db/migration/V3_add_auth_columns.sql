ALTER TABLE users
    ADD email varchar(25)  NOT NULL UNIQUE,
    ADD password varchar(120) NOT NULL;