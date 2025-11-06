-- liquibase formatted sql

-- changeset gpassos:0003-migrate-data
update "users"
set user_id = gen_random_uuid()
where user_id is null;
--rollback delete from "users" where user_id is not null;

update reports r
set user_id = (select u.user_id
                from "users" u
                where u.external_id1 = r.external_id1)
where r.user_id is null;
--rollback delete from reports where user_id is not null;
