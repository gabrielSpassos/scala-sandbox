-- liquibase formatted sql

-- changeset gpassos:0002-update-tables
alter table "users" add column user_id uuid null;
alter table "users" add column cpf text null;
alter table reports add column user_id uuid null;
-- rollback drop table users;