package ru.tinkoff.edu.java.scrapper.service.jdbc;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.domain.dto.Link;
import ru.tinkoff.edu.java.scrapper.domain.dto.TrackLink;
import ru.tinkoff.edu.java.scrapper.domain.repository.ListLinksRepository;
import ru.tinkoff.edu.java.scrapper.domain.repository.LinkRepository;
import ru.tinkoff.edu.java.scrapper.service.LinksService;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JdbcLinksService implements LinksService {

    private final LinkRepository linkRepository;
    private final ListLinksRepository listLinksRepository;
    @Override
    public Link add(long tgChatId, URI url) {
        Link link = linkRepository.add(url.toString(), OffsetDateTime.now());
        listLinksRepository.add(tgChatId, link.getId());
        return link;
    }

    @Override
    public Link remove(long tgChatId, URI url) {
        Link link = linkRepository.findByUrl(url.toString());
        listLinksRepository.remove(tgChatId, link.getId());
        if(listLinksRepository.findAllByLinkId(link.getId()).isEmpty()){
            linkRepository.remove(link.getId());
        }
        return link;
    }

    @Override
    public Collection<Link> listAll(long tgChatId) {
        return linkRepository.findAllByChat(tgChatId);
    }

}
