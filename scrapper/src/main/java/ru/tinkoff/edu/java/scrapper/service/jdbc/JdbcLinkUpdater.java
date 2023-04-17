package ru.tinkoff.edu.java.scrapper.service.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.domain.dto.Link;
import ru.tinkoff.edu.java.scrapper.domain.dto.TrackLink;
import ru.tinkoff.edu.java.scrapper.domain.repository.ChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.repository.LinkRepository;
import ru.tinkoff.edu.java.scrapper.domain.repository.ListLinksRepository;
import ru.tinkoff.edu.java.scrapper.service.LinkUpdater;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JdbcLinkUpdater implements LinkUpdater {

    private final LinkRepository linkRepository;
    private final ListLinksRepository listLinksRepository;



    @Override
    public int update(List<Link> links) {
        for (Link link: links) {
            linkRepository.update(link.getId());
        }
        return links.size();
    }

    @Override
    public List<Link> findOld() {
        return linkRepository.findOld();
    }

    @Override
    public Map<Link, List<Long>> findUpdates(List<Link> links) {
        Map<Link, List<Long>> map = new HashMap<>();
        for(Link link: links){
            map.put(link, new ArrayList<>());
            var tracks = listLinksRepository.findAllByLinkId(link.getId());
            for(TrackLink ln: tracks){
                map.get(link).add(ln.getTg_chat().getTg_chat());
            }
        }
        return map;
    }
}
