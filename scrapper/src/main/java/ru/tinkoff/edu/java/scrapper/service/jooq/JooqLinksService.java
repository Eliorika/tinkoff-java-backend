package ru.tinkoff.edu.java.scrapper.service.jooq;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.domain.dto.Link;
import ru.tinkoff.edu.java.scrapper.domain.repository.jooq.JooqLinkRepository;
import ru.tinkoff.edu.java.scrapper.domain.repository.jooq.JooqListLinksRepository;
import ru.tinkoff.edu.java.scrapper.service.LinksService;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Collection;


@RequiredArgsConstructor
public class JooqLinksService implements LinksService {

    private final JooqLinkRepository linkRepository;
    private final JooqListLinksRepository listLinksRepository;
    @Override
    public Link add(long tgChatId, URI url) {
        Link link = linkRepository.add(url.toString(), OffsetDateTime.now());
        listLinksRepository.add(tgChatId, link.getLink_id());
        return link;
    }

    @Override
    public Link remove(long tgChatId, URI url) {
        Link link = linkRepository.findByUrl(url.toString());
        listLinksRepository.remove(tgChatId, link.getLink_id());
        if(listLinksRepository.findAllByLinkId(link.getLink_id()).isEmpty()){
            linkRepository.remove(link.getLink_id());
        }
        return link;
    }

    @Override
    public Collection<Link> listAll(long tgChatId) {
        return linkRepository.findAllByChat(tgChatId);
    }

}
