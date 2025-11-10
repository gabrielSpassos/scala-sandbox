-- liquibase formatted sql

-- changeset gpassos:0001-create-tables
create table if not exists tags_v1 (
    id           text not null primary key,
    enabled      boolean not null,
    created_at   timestamp not null default now(),
    updated_at   timestamp not null default now()
);

create table if not exists tags_v2 (
    id           text not null primary key,
    enabled_at   timestamp,
    disabled_at  timestamp,
    created_at   timestamp not null default now(),
    updated_at   timestamp not null default now()
);
--rollback drop table tags_v1;
