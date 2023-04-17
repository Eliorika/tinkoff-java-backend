--liquibase formatted sql

--changeset create_database:1
create table if not exists chats
(
    tg_chat bigint primary key
);