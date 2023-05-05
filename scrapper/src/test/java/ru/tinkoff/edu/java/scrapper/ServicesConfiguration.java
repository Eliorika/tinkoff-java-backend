package ru.tinkoff.edu.java.scrapper;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.*;
import ru.tinkoff.edu.java.scrapper.domain.repository.jpa.JpaChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.repository.jpa.JpaLinkRepository;
import ru.tinkoff.edu.java.scrapper.service.jdbc.JdbcLinkUpdater;
import ru.tinkoff.edu.java.scrapper.service.jooq.JooqLinkUpdater;
import ru.tinkoff.edu.java.scrapper.service.jpa.JpaChatService;
import ru.tinkoff.edu.java.scrapper.service.jpa.JpaLinkService;
import ru.tinkoff.edu.java.scrapper.service.jpa.JpaLinksUpdater;

@Configuration
@ComponentScan(basePackages = "ru.tinkoff.edu.java.scrapper.domain")
public class ServicesConfiguration {
    @Bean
    @Primary
    public JpaLinkService jpaLinkService(JpaLinkRepository jpaLinkRepository, JpaChatRepository jpaChatRepository) {
        return new JpaLinkService(jpaChatRepository, jpaLinkRepository);
    }

    @Bean
    @Primary
    public JpaLinksUpdater jpaLinkUpdater(JpaLinkRepository jpaLinkRepository) {
        return new JpaLinksUpdater(jpaLinkRepository);
    }

    @Bean
    @Primary
    public JpaChatService jpaChatService(JpaLinkRepository jpaLinkRepository, JpaChatRepository jpaChatRepository) {
        return new JpaChatService(jpaChatRepository, jpaLinkRepository);
    }


}
