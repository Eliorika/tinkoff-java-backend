--liquibase formatted sql

--changeset create_database:1
create table if not exists CHATS
(
    TG_CHAT bigint primary key
);