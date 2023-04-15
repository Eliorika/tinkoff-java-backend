--liquibase formatted sql

--changeset create_database:1
create sequence if not exists track_link_sequence start 1;
create table if not exists track_links
(
    id          bigint default nextval('track_link_sequence') primary key,
    link_id     bigint not null,
    chat_id     bigint not null,
    foreign key (link_id) references links(id)
);