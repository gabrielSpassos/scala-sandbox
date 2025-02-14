create table if not exists card (
    id uuid not null default uuid() primary key,
    institution_name varchar(50),
    brand varchar(50),
    name varchar(150),
    number varchar(16) unique,
    expiration_date date,
    cvv varchar(3)
);

create table if not exists bank (
    id uuid not null default uuid() primary key,
    code varchar(10) unique,
    name varchar(150)
);