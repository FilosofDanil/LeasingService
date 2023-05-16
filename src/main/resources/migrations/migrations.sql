create type roles as enum ('SEARCHER', 'LODGER');

alter type roles owner to postgres;

create table if not exists сredentials
(
    id               bigint
        primary key,
    profile_name     varchar(40)  not null,
    surname          varchar(40)  not null,
    phone            varchar(20)  not null,
    email            varchar(100) not null,
    profile_password varchar(100) not null,
    date_of_birth    date         not null,
    verified         boolean,
    activation_code  varchar(100)
);

alter table сredentials
    owner to postgres;

alter sequence credits_id_seq owned by сredentials.id;

create table if not exists searchers
(
    id            bigserial
        primary key,
    credit_id     bigint               not null
        constraint credit_id
            references сredentials,
    city          varchar(100),
    notifications boolean default true not null
);

alter table searchers
    owner to postgres;

create table if not exists images
(
    id   bigserial
        primary key,
    link text not null
);

alter table images
    owner to postgres;

create table if not exists leaseholders
(
    id         bigint default nextval('lodgers_id_seq'::regclass) not null
        constraint lodgers_pkey
            primary key,
    credit_id  bigint                                             not null
        constraint credits
            references сredentials,
    firma_name varchar(100)
);

alter table leaseholders
    owner to postgres;

alter sequence lodgers_id_seq owned by leaseholders.id;

create table if not exists credentials_roles
(
    id             bigserial
        primary key,
    credentials_id bigint not null
        constraint credits
            references сredentials,
    roles          varchar
);

alter table credentials_roles
    owner to postgres;

create table if not exists offers
(
    id             bigserial
        primary key,
    title          varchar(100)     not null,
    post_date      date             not null,
    cold_arend     double precision not null,
    warm_arend     double precision not null,
    description    text,
    city           varchar(100)     not null,
    address        varchar(100)     not null,
    rooms          integer          not null,
    area           double precision not null,
    internet       boolean,
    balkoon        boolean,
    floor          integer          not null,
    image_id       bigint           not null
        constraint image
            references images,
    leaseholder_id bigint           not null
        constraint leaseholder
            references leaseholders
);

alter table offers
    owner to postgres;

create table if not exists liked
(
    id          bigserial
        primary key,
    offer_id    bigint not null
        constraint offer
            references offers,
    searcher_id bigint not null
        constraint searcher
            references searchers
);

alter table liked
    owner to postgres;

create table if not exists appointments
(
    id             bigserial
        primary key,
    offer_id       bigint not null
        constraint offer
            references offers,
    leaseholder_id bigint not null
        constraint lodger
            references leaseholders,
    meeting_date   date   not null,
    meeting_time   time   not null,
    description    text
);

alter table appointments
    owner to postgres;

create table if not exists assignments
(
    id             bigserial
        primary key,
    searcher_id    bigint                not null
        constraint offer
            references searchers,
    appointment_id bigint                not null
        constraint lodger
            references appointments,
    notified       boolean default false not null
);

alter table assignments
    owner to postgres;