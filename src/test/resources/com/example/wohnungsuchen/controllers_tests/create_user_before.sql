insert into сredentials(id,profile_name, surname, phone, email, profile_password, date_of_birth, verified)
values (1, 'dru', 'dru', '+380282777781', 'serhio3347@gmail.com', '123456789', '2004/04/28', true);

insert into credentials_roles(id, credentials_id, roles)
values (1, 1, 'SEARCHER');

insert into  searchers(credit_id)
values(1)