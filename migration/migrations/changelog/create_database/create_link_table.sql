--liquibase formatted sql

--changeset create_database:1
create sequence if not exists LINKS_SEQUENCE start with 1;

create table if not exists LINKS


(
    LINK_ID      bigint default nextval('LINKS_SEQUENCE') primary key,
    LINK         varchar(255) not null unique,
    LAST_CHECKED timestamp    not null
);