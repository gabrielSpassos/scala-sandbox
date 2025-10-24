-- liquibase formatted sql

-- changeset gpassos:0001-create-tables
create table if not exists "users" (
    id           bigint generated always as identity primary key,
    external_id1 text unique not null,
    external_id2 text not null,
    status       text not null,
    created_at   timestamp not null default now(),
    updated_at   timestamp not null default now()
);
--rollback drop table users;

create table if not exists reports (
    id           bigint generated always as identity primary key,
    external_id1 text not null,
    content      text not null,
    created_at   timestamp not null default now(),
    updated_at   timestamp not null default now()
);
--rollback drop table reports;
