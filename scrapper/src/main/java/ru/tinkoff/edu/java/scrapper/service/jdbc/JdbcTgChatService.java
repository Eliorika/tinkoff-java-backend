package ru.tinkoff.edu.java.scrapper.service.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.domain.dto.TrackLink;
import ru.tinkoff.edu.java.scrapper.domain.repository.jdbc.JdbcTemplateChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.repository.jdbc.JdbcTemplateLinkRepository;
import ru.tinkoff.edu.java.scrapper.domain.repository.jdbc.JdbcTemplateListLinksRepository;
import ru.tinkoff.edu.java.scrapper.service.TgChatService;

import java.util.List;


@RequiredArgsConstructor
public class JdbcTgChatService implements TgChatService {
    private final JdbcTemplateChatRepository chatRepository;
    private final JdbcTemplateLinkRepository linkRepository;
    private final JdbcTemplateListLinksRepository listLinksRepository;
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
