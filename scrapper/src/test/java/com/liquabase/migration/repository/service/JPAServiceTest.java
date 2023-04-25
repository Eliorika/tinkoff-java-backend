package com.liquabase.migration.repository.service;

import com.liquabase.migration.IntegrationEnvironment;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import ru.tinkoff.edu.java.scrapper.ScrapperApplication;
import ru.tinkoff.edu.java.scrapper.domain.etities.ChatEntity;
import ru.tinkoff.edu.java.scrapper.domain.repository.jpa.JpaChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.repository.jpa.JpaLinkRepository;
import ru.tinkoff.edu.java.scrapper.service.jpa.JpaChatService;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest(excludeAutoConfiguration = LiquibaseAutoConfiguration.class)
@Import(IntegrationEnvironment.EnvironmentConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
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
