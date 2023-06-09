package ru.tinkoff.edu.java.scrapper.service.jpa;

import lombok.RequiredArgsConstructor;
import ru.tinkoff.edu.java.scrapper.domain.etities.ChatEntity;
import ru.tinkoff.edu.java.scrapper.domain.etities.LinkEntity;
import ru.tinkoff.edu.java.scrapper.domain.repository.jpa.JpaChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.repository.jpa.JpaLinkRepository;
import ru.tinkoff.edu.java.scrapper.service.TgChatService;

import java.util.List;

@RequiredArgsConstructor
public class JpaChatService implements TgChatService {
    private final JpaChatRepository jpaChatRepository;
    private final JpaLinkRepository jpaLinkRepository;
    @Override
    public void register(long tgChatId) {

        if(jpaChatRepository.findById(tgChatId).isEmpty()){
            ChatEntity chat =  new ChatEntity();
            chat.setChat(tgChatId);
            jpaChatRepository.save(chat);
        }
    }

    @Override
    public void unregister(long tgChatId) {
        ChatEntity chat = jpaChatRepository.findById(tgChatId).orElse(null);
        if(chat != null){
            jpaChatRepository.delete(chat);
        }
    }
}
