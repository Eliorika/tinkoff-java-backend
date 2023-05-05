package ru.tinkoff.edu.java.scrapper.configuration.databaseconfig;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.domain.repository.jooq.JooqChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.repository.jooq.JooqLinkRepository;
import ru.tinkoff.edu.java.scrapper.domain.repository.jooq.JooqListLinksRepository;
import ru.tinkoff.edu.java.scrapper.service.LinkUpdater;
import ru.tinkoff.edu.java.scrapper.service.LinksService;
import ru.tinkoff.edu.java.scrapper.service.TgChatService;
import ru.tinkoff.edu.java.scrapper.service.jooq.JooqLinkUpdater;
import ru.tinkoff.edu.java.scrapper.service.jooq.JooqLinksService;
import ru.tinkoff.edu.java.scrapper.service.jooq.JooqTgChatService;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jooq")
public class JooqAccessConfiguration {
    @Bean
    public LinksService linkService(JooqListLinksRepository listLinksRepository,
                                    JooqLinkRepository linkRepository) {
        return new JooqLinksService(linkRepository,listLinksRepository);
    }

    @Bean
    public LinkUpdater linkUpdater(JooqLinkRepository linkRepository, JooqListLinksRepository chatLinkRepository) {
        return new JooqLinkUpdater(linkRepository, chatLinkRepository);
    }

    @Bean
    public TgChatService chatService(JooqListLinksRepository listLinksRepository,
                                     JooqLinkRepository linkRepository,
                                     JooqChatRepository chatRepository) {
        return new JooqTgChatService(chatRepository, linkRepository, listLinksRepository);
    }

}
