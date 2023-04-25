package ru.tinkoff.edu.java.scrapper.service.jpa;

import lombok.RequiredArgsConstructor;
import ru.tinkoff.edu.java.scrapper.domain.dto.Link;
import ru.tinkoff.edu.java.scrapper.domain.etities.LinkEntity;
import ru.tinkoff.edu.java.scrapper.domain.repository.jpa.JpaLinkRepository;
import ru.tinkoff.edu.java.scrapper.service.LinkUpdater;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class JpaLinksUpdater implements LinkUpdater {
    private final JpaLinkRepository linkRepository;

    @Override
    public int update(List<Link> links) {
        for(Link ln: links){
            var link = linkRepository.findByLink(ln.getLink().toString());
            link.setLastUpdated(OffsetDateTime.now());
            linkRepository.save(link);
        }
        return links.size();
    }

    @Override
    public List<Link> findOld() {
        var links = linkRepository.findOld();
        List<Link> list = new ArrayList<>();
        for(LinkEntity ln: links){
            list.add(new Link(ln.getLink_id(), ln.getLink(), ln.getLastUpdated()));
        }
        return list;
    }

    @Override
    public Map<Link, List<Long>> findUpdates(List<Link> links) {
        Map<Link, List<Long>> map = new HashMap<>();
        for(Link link: links){
            map.put(link, new ArrayList<>());
            var ln = linkRepository.findById(link.getLink_id()).orElse(null);
            if (ln != null) {
                for(var chat : ln.getChats()){
                    if(!map.get(link).contains(chat.getChat())){
                        map.get(link).add(chat.getChat());
                    }
                }
            }
        }
        return map;
    }
}
