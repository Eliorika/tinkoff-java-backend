package com.liquabase.migration.repository.service;

import com.liquabase.migration.IntegrationEnvironment;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.tinkoff.edu.java.scrapper.domain.etities.ChatEntity;
import ru.tinkoff.edu.java.scrapper.domain.repository.jpa.JpaChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.repository.jpa.JpaLinkRepository;
import ru.tinkoff.edu.java.scrapper.service.jpa.JpaChatService;
import ru.tinkoff.edu.java.scrapper.service.jpa.JpaLinkService;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {IntegrationEnvironment.EnvironmentConfig.class, JpaChatRepository.class, JpaChatService.class,
JpaLinkRepository.class})
public class JPAServiceTest extends IntegrationEnvironment {

    @Autowired
    private JpaChatRepository chatRepository;

    @Autowired
    private JpaLinkRepository linkRepository;

@Autowired
    private JpaChatService jpaChatService;


    @Test
    public void registrationTest(){
        long id = 123;
        jpaChatService.register(id);
        ChatEntity chat = new ChatEntity();
        chat.setChat(id);

        assertTrue(chatRepository.findById(id).orElse(null)!= null);
    }
}
