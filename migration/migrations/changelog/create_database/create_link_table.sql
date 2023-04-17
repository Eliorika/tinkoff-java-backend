--liquibase formatted sql

--changeset create_database:1
create sequence if not exists links_sequence start 1;
create table if not exists links
(
    link_id      bigint default nextval('links_sequence') primary key,
    link         varchar(255) not null unique,
    last_checked timestamp    not null
);