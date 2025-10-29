-- liquibase formatted sql

-- changeset gpassos:0002-update-tables
alter table "users" add column user_id uuid null;
--rollback alter table "users" drop column user_id;

alter table "users" add column cpf text null;
--rollback alter table "users" drop column cpf;

alter table "users" alter column external_id1 drop not null;
--rollback alter table users alter column external_id1 set not null;

alter table "users" alter column external_id2 drop not null;
--rollback alter table users alter column external_id2 set not null;

alter table reports add column user_id uuid null;
--rollback alter table reports drop column user_id;

alter table reports alter column external_id1 drop not null;
--rollback alter table reports alter column external_id1 set not null;
