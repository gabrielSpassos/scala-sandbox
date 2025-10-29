-- liquibase formatted sql

-- changeset gpassos:0004-update-constraints
alter table "users" alter column external_id1 drop not null;
--rollback alter table users alter column external_id1 set not null;

alter table "users" alter column external_id2 drop not null;
--rollback alter table users alter column external_id2 set not null;

alter table reports alter column external_id1 drop not null;
--rollback alter table reports alter column external_id1 set not null;
