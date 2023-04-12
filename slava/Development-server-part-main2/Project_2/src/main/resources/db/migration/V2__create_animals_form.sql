
INSERT INTO form(name)
VALUES
    ('one');
INSERT INTO form(name)
VALUES
    ('two');
INSERT INTO form(name)
VALUES
    ('three');

INSERT INTO animal(id, name, animal_form) VALUES (uuid_generate_v1(),'cat','one');
INSERT INTO animal(id, name, animal_form) VALUES (uuid_generate_v1(),'cat2','one');
INSERT INTO animal(id, name, animal_form) VALUES (uuid_generate_v1(),'cat3','one');