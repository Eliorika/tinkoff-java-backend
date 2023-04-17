package ru.tinkoff.edu.java.scrapper.service.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.domain.dto.Link;
import ru.tinkoff.edu.java.scrapper.domain.dto.TrackLink;
import ru.tinkoff.edu.java.scrapper.domain.repository.ChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.repository.LinkRepository;
import ru.tinkoff.edu.java.scrapper.domain.repository.ListLinksRepository;
import ru.tinkoff.edu.java.scrapper.service.TgChatService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JdbcTgChatService implements TgChatService {
    private final ChatRepository chatRepository;
    private final LinkRepository linkRepository;
    private final ListLinksRepository listLinksRepository;
    @Override
    public void register(long tgChatId) {
        chatRepository.add(tgChatId);
    }

    @Override
    public void unregister(long tgChatId) {
        List<TrackLink> linkList = listLinksRepository.findAllByChatId(tgChatId);
        for(TrackLink trackLink: linkList){
            listLinksRepository.remove(tgChatId, trackLink.getLink().getId());
            if(listLinksRepository.findAllByLinkId(trackLink.getLink().getId()).isEmpty()){
                linkRepository.remove(trackLink.getLink().getId());
            }
        }
        chatRepository.remove(tgChatId);
    }
}
