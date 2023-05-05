package ru.tinkoff.edu.java.scrapper.domain.repository;

import ru.tinkoff.edu.java.scrapper.domain.dto.Link;

import java.time.OffsetDateTime;
import java.util.List;

public interface LinkRepo {

    List<Link> findAll();
    List<Link> findAllByChat(long chat_id);
    Link findByUrl(String url);
    Link add(String link, OffsetDateTime update);
    void remove(long id);
    void update(long id);
    List<Link> findOld();
}
