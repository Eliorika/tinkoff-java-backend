package ru.tinkoff.edu.java.scrapper.domain.repository;

import ru.tinkoff.edu.java.scrapper.domain.dto.Chat;

import java.util.List;

public interface ChatRepo {

    List<Chat> findAll();

    Chat findById(Long id);

    Chat add(Long id);

    void remove(Long id);
}
