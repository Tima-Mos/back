INSERT INTO public.roles (name) VALUES ('ROLE_USER');
INSERT INTO public.roles (name) VALUES ('ROLE_ADMIN');
INSERT INTO public.roles (name) VALUES ('ROLE_MODERN');
INSERT INTO public.users (id, email, enabled, first_name, last_name, password, role) VALUES ('9326e0a3-0480-4268-a01d-6507960afd26', 'admin@bk.ru', true, 'slava', 'kurenkov', '$2a$12$MXw3DDiSfN5PtEMYiA55meAAOeywuiXKqjbqfLB853ZGpWY8DeN7m', 'ROLE_ADMIN');
INSERT INTO public.users (id, email, enabled, first_name, last_name, password, role) VALUES ('71c028d7-3966-43e3-946a-2c2574fe5bff', 'superman@mail.ru', true, 'pavel', 'hrabr', '$2a$12$MXw3DDiSfN5PtEMYiA55meAAOeywuiXKqjbqfLB853ZGpWY8DeN7m', 'ROLE_USER');