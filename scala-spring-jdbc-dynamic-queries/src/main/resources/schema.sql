create table if not exists card (
    id uuid not null primary key default gen_random_uuid(),
    institution_name varchar(50),
    brand varchar(50),
    name varchar(150),
    number varchar(16) unique,
    expiration_date date,
    cvv varchar(3),
    soft_deleted boolean default false
);

create table if not exists bank (
    id uuid not null primary key default gen_random_uuid(),
    code varchar(10) unique,
    name varchar(150),
    soft_deleted boolean default false
);