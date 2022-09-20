-- liquibase formatted sql
-- changeset vzaremba:person_name_surname_index

CREATE INDEX persons_name_surname_i
    ON persons (name(15), surname(15));
