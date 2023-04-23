package ru.tinkoff.edu.java.scrapper.service.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.domain.etities.ChatEntity;
import ru.tinkoff.edu.java.scrapper.domain.etities.LinkEntity;
import ru.tinkoff.edu.java.scrapper.domain.repository.jdbc.JdbcTemplateLinkRepository;
import ru.tinkoff.edu.java.scrapper.domain.repository.jpa.JpaChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.repository.jpa.JpaLinkRepository;
import ru.tinkoff.edu.java.scrapper.service.TgChatService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class JpaChatService implements TgChatService {
    private final JpaChatRepository chatRepository;
    private final JpaLinkRepository linkRepository;
    @Override
    public void register(long tgChatId) {

        if(chatRepository.findById(tgChatId).isEmpty()){
            ChatEntity chat =  new ChatEntity();
            chat.setChat(tgChatId);
            chatRepository.save(chat);
        }
    }

    @Override
    public void unregister(long tgChatId) {
        ChatEntity chat = chatRepository.findById(tgChatId).orElse(null);
        if(chat != null){
            List<LinkEntity> links = linkRepository.findAllByChats(tgChatId);
            linkRepository.deleteAll(links);
            chatRepository.delete(chat);
        }
    }
}
