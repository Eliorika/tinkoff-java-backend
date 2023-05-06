package ru.tinkoff.edu.java.scrapper.service.jdbc;

import lombok.RequiredArgsConstructor;
import ru.tinkoff.edu.java.scrapper.domain.dto.Link;
import ru.tinkoff.edu.java.scrapper.domain.dto.TrackLink;
import ru.tinkoff.edu.java.scrapper.domain.repository.jdbc.JdbcTemplateLinkRepository;
import ru.tinkoff.edu.java.scrapper.domain.repository.jdbc.JdbcTemplateListLinksRepository;
import ru.tinkoff.edu.java.scrapper.service.LinkUpdater;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
public class JdbcLinkUpdater implements LinkUpdater {

    private final JdbcTemplateLinkRepository linkRepository;
    private final JdbcTemplateListLinksRepository listLinksRepository;



    @Override
    public int update(List<Link> links) {
        for (Link link: links) {
            linkRepository.update(link.getLink_id());
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
            var tracks = listLinksRepository.findAllByLinkId(link.getLink_id());
            for(TrackLink ln: tracks){
                map.get(link).add(ln.getTg_chat().getTg_chat());
            }
        }
        return map;
    }
}
