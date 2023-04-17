package com.liquabase.migration.repository;

import com.liquabase.migration.IntegrationEnvironment;
import org.jooq.impl.QOM;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.JdbcDatabaseContainer;
import ru.tinkoff.edu.java.scrapper.ScrapperApplication;
import ru.tinkoff.edu.java.scrapper.domain.dto.Link;
import ru.tinkoff.edu.java.scrapper.domain.repository.LinkRepository;

import javax.sql.DataSource;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;


@SpringBootTest(classes = {IntegrationEnvironment.EnvironmentConfig.class, LinkRepository.class})
public class LinkTestIT extends IntegrationEnvironment {

    @Autowired
    private LinkRepository linkRepository;

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

        linkRepository.remove(rmLink.getId());

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
