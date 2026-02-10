CREATE TABLE IF NOT EXISTS items(
  id            uuid not null primary key default gen_random_uuid(),
  external_id   TEXT NOT NULL UNIQUE,
  value         TEXT NOT NULL,
  updated_at    TIMESTAMP NOT NULL
);
