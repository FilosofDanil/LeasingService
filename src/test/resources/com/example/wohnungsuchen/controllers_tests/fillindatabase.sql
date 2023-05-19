insert into сredentials(id, profile_name, surname, phone, email, profile_password, date_of_birth, verified)
values (1, 'dru', 'dru', '+380282777781', 'serhio3347@gmail.com', '123456789', '2004/04/28', true);

insert into credentials_roles(id, credentials_id, roles)
values (1, 1, 'SEARCHER');

insert into searchers(id, credit_id)
values (1, 1);

insert into сredentials(id, profile_name, surname, phone, email, profile_password, date_of_birth, verified)
values (2, 'andrew', 'andrew', '+380282777781', 'uspehagora@gmail.com', '123456789', '2004/04/28', true);

insert into credentials_roles(id, credentials_id, roles)
values (2, 2, 'LEASEHOLDER');

insert into leaseholders(id, credit_id)
values (1, 2);

insert into images(link) values ('base.png');

insert into offers(id, title, post_date, cold_arend, warm_arend, description, city, address, rooms, area, internet,
                   balkoon, floor, image_id, leaseholder_id)
values (1, 'House of the rising sun', '2023/05/16', 100.0, 250.0, 'Some text', 'Vinitsya', 'Gercena35', 1, 30.0, true,
        true, 1, 1, 1),
(2, 'House of the rising sun', '2023/05/16', 100.0, 300.0, 'Some text', 'Hamburg', 'Gercena35', 2, 32.5, true,
        true, 2, 1, 1),
(3, 'House of the rising sun', '2023/05/16', 100.0, 350.0, 'Some text', 'Hamburg', 'Gercena35', 3, 35.0, true,
        true, 3, 1, 1),
(4, 'House of the rising sun', '2023/05/16', 100.0, 400.0, 'Some text', 'Vinitsya', 'Gercena35', 1, 40.0, true,
        true, 4, 1, 1),
(5, 'House of the rising sun', '2023/05/16', 100.0, 450.0, 'Some text', 'Vinitsya', 'Gercena35', 2, 45.0, true,
        true, 5, 1, 1),
(6, 'House of the rising sun', '2023/05/16', 100.0, 500.0, 'Some text', 'Hamburg', 'Gercena35', 3, 50.0, true,
        true, 6, 1, 1),
(7, 'House of the rising sun', '2023/05/16', 100.0, 550.0, 'Some text', 'Hamburg', 'Gercena35', 1, 30.0, true,
        true, 7, 1, 1),
(8, 'House of the rising sun', '2023/05/16', 100.0, 600.0, 'Some text', 'Vinitsya', 'Gercena35', 2, 32.5, true,
        true, 8, 1, 1),
(9, 'House of the rising sun', '2023/05/16', 100.0, 650.0, 'Some text', 'Hamburg', 'Gercena35', 3, 35.0, true,
        true, 9, 1, 1),
(10, 'House of the rising sun', '2023/05/16', 100.0, 700.0, 'Some text', 'Vinitsya', 'Gercena35', 1, 40.0, true,
        true, 10, 1, 1),
(11, 'House of the rising sun', '2023/05/16', 100.0, 750.0, 'Some text', 'Hamburg', 'Gercena35', 2, 45.0, true,
        true, 11, 1, 1),
(12, 'House of the rising sun', '2023/05/16', 100.0, 800.0, 'Some text', 'Hamburg', 'Gercena35', 3, 50.0, true,
        true, 12, 1, 1)