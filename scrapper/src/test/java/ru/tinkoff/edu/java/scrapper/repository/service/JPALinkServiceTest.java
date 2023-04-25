package ru.tinkoff.edu.java.scrapper.repository.service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.ServicesConfiguration;
import ru.tinkoff.edu.java.scrapper.domain.repository.jpa.JpaChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.repository.jpa.JpaLinkRepository;
import ru.tinkoff.edu.java.scrapper.repository.IntegrationEnvironment;
import ru.tinkoff.edu.java.scrapper.service.jpa.JpaChatService;
import ru.tinkoff.edu.java.scrapper.service.jpa.JpaLinkService;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DataJpaTest(excludeAutoConfiguration = LiquibaseAutoConfiguration.class)
@Import({IntegrationEnvironment.JpaConfig.class, ServicesConfiguration.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@SpringBootTest(classes = {IntegrationEnvironment.EnvironmentConfig.class, ServicesConfiguration.class,
        //JpaChatRepository.class, JpaChatService.class, JpaLinkRepository.class})
//@SpringBootTest(classes = IntegrationEnvironment.EnvironmentConfig.class)
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
//@ContextConfiguration(classes = {ServicesConfiguration.class})
public class JPALinkServiceTest extends IntegrationEnvironment {

    @Autowired
    private JpaChatRepository jpaChatRepository;

    @Autowired
    private JpaLinkRepository jpaLinkRepository;

    @Autowired
    private JpaLinkService jpaLinkService;

    @Autowired
    private JpaChatService jpaChatService;


    @Test
    @Transactional
    public void addLinkTest(){
        long id = 222;
        jpaChatService.register(id);
        String link = "localhost.ru";
        try {
            URI url = new URI(link);
            jpaLinkService.add(id, url);
            jpaChatRepository.flush();
            jpaLinkRepository.flush();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        var actual = jpaLinkRepository.findByLink(link).getLink();

        assertEquals(link, actual);
    }

    @Test
    @Transactional
    public void  removeLinkTest(){
        long id = 222;
        jpaChatService.register(id);
        String link = "localhost.ru";
        String link2 = "localhost2.ru";
        try {
            URI url = new URI(link);
            URI url2 = new URI(link2);
            jpaLinkService.add(id, url);
            jpaLinkService.add(id, url2);
            //jpaChatRepository.flush();
           // jpaLinkRepository.flush();

            jpaLinkService.remove(id, url);
            jpaChatRepository.flush();
            jpaLinkRepository.flush();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        var list = jpaLinkRepository.findAll();
        assertEquals(1, list.size());
        assertEquals(link2, list.get(0).getLink());
    }

    @Test
    @Transactional
    public void  listLinkTest(){
        long id = 222;
        jpaChatService.register(id);
        String link = "localhost.ru";
        String link2 = "localhost2.ru";
        try {
            URI url = new URI(link);
            URI url2 = new URI(link2);
            jpaLinkService.add(id, url);
            jpaLinkService.add(id, url2);
            jpaChatRepository.flush();
            jpaLinkRepository.flush();

        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        var list = jpaLinkRepository.findAll();
        assertEquals(2, list.size());
        assertEquals(link, list.get(0).getLink());
        assertEquals(link2, list.get(1).getLink());
    }



}
