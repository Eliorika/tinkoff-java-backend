package ru.tinkoff.edu.java.scrapper.domain.repository;

import ru.tinkoff.edu.java.scrapper.domain.dto.TrackLink;

import java.util.List;

public interface ListLinksRepo {
    List<TrackLink> findAll();
    List<TrackLink> findAllByChatId(long chatId);
    List<TrackLink> findAllByLinkId(long linkId);
    void add(long chatId, long linkId);
    void remove(long chatId, long linkId);


}
