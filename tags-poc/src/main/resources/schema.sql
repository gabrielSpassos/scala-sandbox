create table if not exists "user" (
    id           bigint generated always as identity primary key,
    user_id      uuid null,
    cpf          text null,
    external_id1 text unique,
    external_id2 text,
    status       text not null,
    created_at   timestamp not null default now(),
    updated_at   timestamp not null default now()
);

create table if not exists report (
    id           bigint generated always as identity primary key,
    user_id      uuid null,
    external_id1 text not null,
    content      text not null,
    created_at   timestamp not null default now(),
    updated_at   timestamp not null default now()
);