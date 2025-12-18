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

create unique index if not exists uq_tags_name_entity_id on tags (name, entity_id);
