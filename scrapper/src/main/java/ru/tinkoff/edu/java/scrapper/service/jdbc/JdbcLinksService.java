package ru.tinkoff.edu.java.scrapper.service.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.domain.dto.Link;
import ru.tinkoff.edu.java.scrapper.domain.repository.jdbc.JdbcTemplateListLinksRepository;
import ru.tinkoff.edu.java.scrapper.domain.repository.jdbc.JdbcTemplateLinkRepository;
import ru.tinkoff.edu.java.scrapper.service.LinksService;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class JdbcLinksService implements LinksService {

    private final JdbcTemplateLinkRepository linkRepository;
    private final JdbcTemplateListLinksRepository listLinksRepository;
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
