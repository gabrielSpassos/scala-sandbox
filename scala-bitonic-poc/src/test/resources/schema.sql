create table if not exists bitonic_sequence (
    id uuid not null primary key default gen_random_uuid(),
    size int not null,
    lower_boundary int not null,
    upper_boundary int not null,
    sequence text not null
);
