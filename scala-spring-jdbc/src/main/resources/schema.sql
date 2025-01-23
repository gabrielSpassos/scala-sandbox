create table if not exists card (
    id uuid not null default uuid() primary key,
    institution_name varchar(50),
    brand varchar(50),
    name varchar(150),
    number varchar(16),
    expiration_date date,
    cvv varchar(3)
);