--liquibase formatted sql

--changeset create_database:1
create sequence if not exists track_link_sequence start 1;
create table if not exists links_list
(
    track_id          bigint default nextval('track_link_sequence') primary key,
    link_id     bigint not null,
    tg_chat     bigint not null,
    foreign key (link_id) references links(link_id),
    foreign key (tg_chat) references chats(tg_chat)
);

alter table links_list add constraint un_track UNIQUE (link_id, tg_chat)