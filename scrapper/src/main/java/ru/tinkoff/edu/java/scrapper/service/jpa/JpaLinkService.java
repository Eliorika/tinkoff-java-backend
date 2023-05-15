package ru.tinkoff.edu.java.scrapper.service.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.domain.dto.Link;
import ru.tinkoff.edu.java.scrapper.domain.etities.ChatEntity;
import ru.tinkoff.edu.java.scrapper.domain.etities.LinkEntity;
import ru.tinkoff.edu.java.scrapper.domain.repository.jpa.JpaChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.repository.jpa.JpaLinkRepository;
import ru.tinkoff.edu.java.scrapper.service.LinksService;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class JpaLinkService implements LinksService {
    private final JpaChatRepository chatRepository;
    private final JpaLinkRepository linkRepository;


    @Override
    @Transactional
    public Link add(long tgChatId, URI url) {
        ChatEntity chat = chatRepository.findById(tgChatId).orElse(null);
        if(chat == null)
            return null;

        var link = linkRepository.findByLink(url.toString());

        if(link == null){
            link = new LinkEntity();
            link.setLink(url.toString());
            link.setLastUpdated(OffsetDateTime.now());
            linkRepository.save(link);
        }

        chat.getLinks().add(link);
        link.getChats().add(chat);
        chatRepository.save(chat);
        linkRepository.save(link);

        return new Link(link.getLink_id(), link.getLink(), link.getLastUpdated());
    }

    @Override
    @Transactional
    public Link remove(long tgChatId, URI url) {
        ChatEntity chat = chatRepository.findById(tgChatId).orElse(null);
        var link = linkRepository.findByLink(url.toString());

        if(chat == null || link == null)
            return null;

        chat.getLinks().remove(link);
        link.getChats().remove(chat);
        chatRepository.save(chat);
        linkRepository.save(link);
        if(link.getChats().size()==0){
            linkRepository.delete(link);
        }

        return new Link(link.getLink_id(), link.getLink(), link.getLastUpdated());
    }

    @Override
    public Collection<Link> listAll(long tgChatId) {
        ChatEntity chat = chatRepository.findById(tgChatId).orElse(null);
        if(chat == null)
            return null;
        var links = chat.getLinks();
        List<Link> list = new ArrayList<>();
        for(LinkEntity ln: links){
            list.add(new Link(ln.getLink_id(), ln.getLink(), ln.getLastUpdated()));
        }
        return list;
    }



}
