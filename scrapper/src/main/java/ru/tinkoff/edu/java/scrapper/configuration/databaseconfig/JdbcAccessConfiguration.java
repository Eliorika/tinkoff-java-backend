package ru.tinkoff.edu.java.scrapper.configuration.databaseconfig;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.domain.repository.jdbc.JdbcTemplateChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.repository.jdbc.JdbcTemplateLinkRepository;
import ru.tinkoff.edu.java.scrapper.domain.repository.jdbc.JdbcTemplateListLinksRepository;
import ru.tinkoff.edu.java.scrapper.service.LinkUpdater;
import ru.tinkoff.edu.java.scrapper.service.LinksService;
import ru.tinkoff.edu.java.scrapper.service.TgChatService;
import ru.tinkoff.edu.java.scrapper.service.jdbc.JdbcLinkUpdater;
import ru.tinkoff.edu.java.scrapper.service.jdbc.JdbcLinksService;
import ru.tinkoff.edu.java.scrapper.service.jdbc.JdbcTgChatService;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
public class JdbcAccessConfiguration {
    @Bean
    public LinksService linkService(JdbcTemplateListLinksRepository listLinksRepository,
                                    JdbcTemplateLinkRepository linkRepository) {
        return new JdbcLinksService(linkRepository,listLinksRepository);
    }

    @Bean
    public LinkUpdater linkUpdater(JdbcTemplateLinkRepository linkRepository,
        JdbcTemplateListLinksRepository chatLinkRepository) {
        return new JdbcLinkUpdater(linkRepository, chatLinkRepository);
    }

    @Bean
    public TgChatService chatService(JdbcTemplateListLinksRepository listLinksRepository,
                                     JdbcTemplateLinkRepository linkRepository,
                                     JdbcTemplateChatRepository chatRepository) {
        return new JdbcTgChatService(chatRepository, linkRepository, listLinksRepository);
    }

}
