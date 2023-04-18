package com.liquabase.migration.repository.jdbc;

import com.liquabase.migration.IntegrationEnvironment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.domain.dto.Link;
import ru.tinkoff.edu.java.scrapper.domain.repository.jdbc.JdbcTemplateLinkRepository;

import java.time.OffsetDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;


@SpringBootTest(classes = {IntegrationEnvironment.EnvironmentConfig.class, JdbcTemplateLinkRepository.class})
public class JdbcLinkTestIT extends IntegrationEnvironment {

    @Autowired
    private JdbcTemplateLinkRepository linkRepository;

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
