-- liquibase formatted sql

-- changeset gpassos:0002-update-tables
alter table "users" add column user_id uuid null;
alter table "users" add column cpf text null;
alter table "users" alter column external_id1 drop not null;
alter table "users" alter column external_id2 drop not null;

alter table reports add column user_id uuid null;
-- rollback drop table users;