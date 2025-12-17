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

create table if not exists tags_v3 (
    id           text not null primary key,
    value        text not null,
    created_at   timestamp not null default now(),
    updated_at   timestamp not null default now()
);

create table if not exists tags (
    id           uuid not null primary key default gen_random_uuid(),
    name         text not null,
    entity_id    text not null,
    value        text not null,
    created_at   timestamp not null default now(),
    updated_at   timestamp not null default now()
);

--rollback drop table tags_v1;
--rollback drop table tags_v2;
--rollback drop table tags_v3;
--rollback drop table tags;
