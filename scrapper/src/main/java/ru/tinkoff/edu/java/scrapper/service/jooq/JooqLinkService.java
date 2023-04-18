package ru.tinkoff.edu.java.scrapper.service.jooq;

import ru.tinkoff.edu.java.scrapper.domain.dto.Link;
import ru.tinkoff.edu.java.scrapper.service.LinksService;

import java.net.URI;
import java.util.Collection;

public class JooqLinkService implements LinksService {
    @Override
    public Link add(long tgChatId, URI url) {
        return null;
    }

    @Override
    public Link remove(long tgChatId, URI url) {
        return null;
    }

    @Override
    public Collection<Link> listAll(long tgChatId) {
        return null;
    }
}
