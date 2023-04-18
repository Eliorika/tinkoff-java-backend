package ru.tinkoff.edu.java.scrapper.service.jooq;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.domain.dto.TrackLink;
import ru.tinkoff.edu.java.scrapper.domain.repository.jooq.JooqChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.repository.jooq.JooqLinkRepository;
import ru.tinkoff.edu.java.scrapper.domain.repository.jooq.JooqListLinksRepository;
import ru.tinkoff.edu.java.scrapper.service.TgChatService;

import java.util.List;

@Service
@Primary
@AllArgsConstructor
public class JooqTgChatService implements TgChatService {

    private final JooqChatRepository chatRepository;
    private final JooqLinkRepository linkRepository;
    private final JooqListLinksRepository listLinksRepository;
    @Override
    public void register(long tgChatId) {
        chatRepository.add(tgChatId);
    }

    @Override
    public void unregister(long tgChatId) {
        List<TrackLink> linkList = listLinksRepository.findAllByChatId(tgChatId);
        for(TrackLink trackLink: linkList){
            listLinksRepository.remove(tgChatId, trackLink.getLink().getLink_id());
            if(listLinksRepository.findAllByLinkId(trackLink.getLink().getLink_id()).isEmpty()){
                linkRepository.remove(trackLink.getLink().getLink_id());
            }
        }
        chatRepository.remove(tgChatId);
    }
}
