package ru.tinkoff.edu.java.scrapper.service;

import ru.tinkoff.edu.java.scrapper.domain.dto.Link;

import java.net.URI;
import java.util.Collection;

public interface LinksService {
    Link add(long tgChatId, URI url);
    Link remove(long tgChatId, URI url);
    Collection<Link> listAll(long tgChatId);
}
