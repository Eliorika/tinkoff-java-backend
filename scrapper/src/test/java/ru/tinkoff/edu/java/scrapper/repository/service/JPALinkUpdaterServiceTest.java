package ru.tinkoff.edu.java.scrapper.repository.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.ServicesConfiguration;
import ru.tinkoff.edu.java.scrapper.domain.dto.Link;
import ru.tinkoff.edu.java.scrapper.domain.repository.jpa.JpaChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.repository.jpa.JpaLinkRepository;
import ru.tinkoff.edu.java.scrapper.repository.IntegrationEnvironment;
import ru.tinkoff.edu.java.scrapper.service.jpa.JpaChatService;
import ru.tinkoff.edu.java.scrapper.service.jpa.JpaLinkService;
import ru.tinkoff.edu.java.scrapper.service.jpa.JpaLinksUpdater;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest(excludeAutoConfiguration = LiquibaseAutoConfiguration.class)
@Import({IntegrationEnvironment.JpaConfig.class, ServicesConfiguration.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@SpringBootTest(classes = {IntegrationEnvironment.EnvironmentConfig.class, ServicesConfiguration.class,
        //JpaChatRepository.class, JpaChatService.class, JpaLinkRepository.class})
//@SpringBootTest(classes = IntegrationEnvironment.EnvironmentConfig.class)
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
//@ContextConfiguration(classes = {ServicesConfiguration.class})
public class JPALinkUpdaterServiceTest extends IntegrationEnvironment {

    @Autowired
    private JpaChatRepository jpaChatRepository;

    @Autowired
    private JpaLinkRepository jpaLinkRepository;

    @Autowired
    private JpaLinkService jpaLinkService;

    @Autowired
    private JpaChatService jpaChatService;

    @Autowired
    private JpaLinksUpdater jpaLinksUpdater;


    @Test
    @Transactional
    public void updateLinkTest(){
        long id = 222;
        jpaChatService.register(id);
        Link link1 = new Link(1, "https://github.com/Eliorika/Elective-courses", OffsetDateTime.now().minusDays(10));
        jpaLinkService.add(id, link1.getLink());
        jpaChatRepository.flush();
        jpaLinkRepository.flush();

        var oldLink = jpaLinkRepository.findByLink(link1.getLink().toString());
        oldLink.setLastUpdated(OffsetDateTime.now().minusDays(5));

        var old = jpaLinksUpdater.findOld();

        assertEquals(1, old.size());
        assertEquals(link1.getLink(), old.get(0).getLink());

        jpaLinksUpdater.update(jpaLinksUpdater.findOld());
        var oldUp = jpaLinksUpdater.findOld();
        assertEquals(0, oldUp.size());
        assertFalse(oldUp.contains(link1));
    }

    @Test
    @Transactional
    public void findOldTest(){
        long id = 222;
        jpaChatService.register(id);
        Link link1 = new Link(1, "https://github.com/Eliorika/Elective-courses", OffsetDateTime.now());
        Link link2 = new Link(2, "https://github.com/Eliorika/tinkoff-java-backend", OffsetDateTime.now());
        jpaLinkService.add(id, link1.getLink());
        jpaLinkService.add(id, link2.getLink());
        jpaChatRepository.flush();
        jpaLinkRepository.flush();

        var oldLink = jpaLinkRepository.findByLink(link1.getLink().toString());
        oldLink.setLastUpdated(OffsetDateTime.now().minusDays(5));

        jpaLinkRepository.save(oldLink);
        jpaLinkRepository.flush();

        var old = jpaLinksUpdater.findOld();

        assertEquals(1, old.size());
        assertEquals(link1.getLink(), old.get(0).getLink());
    }


    @Test
    @Transactional
    public void findUpdatesTest(){
        long id = 222;
        long id2 = 11;
        jpaChatService.register(id);
        jpaChatService.register(id2);
        Link link1 = new Link(1, "https://github.com/Eliorika/Elective-courses", OffsetDateTime.now());
        jpaLinkService.add(id, link1.getLink());
        jpaLinkService.add(id2, link1.getLink());
        jpaChatRepository.flush();
        jpaLinkRepository.flush();

        var oldLink = jpaLinkRepository.findByLink(link1.getLink().toString());
        oldLink.setLastUpdated(OffsetDateTime.now().minusDays(5));

        jpaLinkRepository.save(oldLink);
        jpaLinkRepository.flush();

        var old = jpaLinksUpdater.findOld();

        var updates = jpaLinksUpdater.findUpdates(old);
        assertEquals(1, updates.size());
        for(var row: updates.entrySet()){
            Link link = row.getKey();
            assertEquals(link1.getLink(), link.getLink());

            var list = updates.get(link);
            assertEquals(2, list.size());
            assertTrue(list.contains(id));
            assertTrue(list.contains(id2));
        }

    }


}
