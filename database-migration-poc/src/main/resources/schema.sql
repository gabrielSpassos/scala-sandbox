create table if not exists user (
    id uuid not null primary key default gen_random_uuid(),
    user_id text null,
    cpf text null,
    external_id1 text unique,
    external_id2 text,
    status text not null,
    created_at timestamp not null default now(),
    updated_at timestamp not null default now()
);

create table if not exist report (
    id uuid not null primary key default gen_random_uuid(),
    user_id text null,
    external_id1 not null,
    content text not null,
    created_at timestamp not null default now(),
    updated_at timestamp not null default now()
);