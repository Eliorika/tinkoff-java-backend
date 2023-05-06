package ru.tinkoff.edu.java.scrapper.configuration.databaseconfig;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.domain.repository.jpa.JpaChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.repository.jpa.JpaLinkRepository;
import ru.tinkoff.edu.java.scrapper.service.LinkUpdater;
import ru.tinkoff.edu.java.scrapper.service.LinksService;
import ru.tinkoff.edu.java.scrapper.service.TgChatService;
import ru.tinkoff.edu.java.scrapper.service.jpa.JpaChatService;
import ru.tinkoff.edu.java.scrapper.service.jpa.JpaLinkService;
import ru.tinkoff.edu.java.scrapper.service.jpa.JpaLinksUpdater;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")

public class JpaAccessConfiguration {
    @Bean
    public LinksService linkService(JpaChatRepository chatRepository,
                                    JpaLinkRepository linkRepository) {
        return new JpaLinkService(chatRepository, linkRepository);
    }

    @Bean
    public LinkUpdater linkUpdater(JpaLinkRepository linkRepository) {
        return new JpaLinksUpdater(linkRepository);
    }

    @Bean
    public TgChatService chatService(JpaLinkRepository linkRepository,
                                     JpaChatRepository chatRepository) {
        return new JpaChatService(chatRepository, linkRepository);
    }

}
