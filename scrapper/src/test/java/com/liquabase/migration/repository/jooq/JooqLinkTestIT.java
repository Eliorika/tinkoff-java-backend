package com.liquabase.migration.repository.jooq;

import com.liquabase.migration.IntegrationEnvironment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.domain.dto.Link;
import ru.tinkoff.edu.java.scrapper.domain.repository.jooq.JooqLinkRepository;

import java.time.OffsetDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@SpringBootTest(classes = {IntegrationEnvironment.EnvironmentConfig.class, JooqLinkRepository.class})
public class JooqLinkTestIT extends IntegrationEnvironment {

    @Autowired
    private JooqLinkRepository linkRepository;

    @Test
    @Transactional
    @Rollback
    public void addLinkTest(){
        String link1 = "github.com";

        linkRepository.add(link1, OffsetDateTime.now());

        var list = linkRepository.findAll();

        assertThat(list, is(not(emptyIterable())));
        assertThat(list.size(), equalTo(1));
        assertThat(list.get(0).getLink().toString(), equalTo(link1));
    }

    @Test
    @Transactional
    @Rollback
    public void removeLinkTest(){
        String link1 = "github.com";
        String link2 = "stackoverflow.com";

        Link rmLink = linkRepository.add(link1, OffsetDateTime.now());
        linkRepository.add(link2, OffsetDateTime.now());

        linkRepository.remove(rmLink.getLink_id());

        var list = linkRepository.findAll();

        assertThat(list, is(not(emptyIterable())));
        assertThat(list.size(), equalTo(1));
        assertThat(list.get(0).getLink().toString(), equalTo(link2));
    }

    @Test
    @Transactional
    @Rollback
    public void findLinkTest(){
        String link1 = "github.com";
        String link2 = "stackoverflow.com";

        linkRepository.add(link1, OffsetDateTime.now());
        linkRepository.add(link2, OffsetDateTime.now());

        linkRepository.findAll();

        var list = linkRepository.findAll();


        assertThat(list, is(not(emptyIterable())));
        assertThat(list.size(), equalTo(2));
        assertThat(list.get(0).getLink().toString(), equalTo(link1));
    }
}
