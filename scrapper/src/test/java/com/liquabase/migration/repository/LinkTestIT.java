package com.liquabase.migration.repository;

import com.liquabase.migration.IntegrationEnvironment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
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

@SpringBootTest(classes = ScrapperApplication.class)
public class LinkTestIT extends IntegrationEnvironment {

    @Autowired
    private LinkRepository linkRepository;

    @Test
    @Transactional
    @Rollback
    public void addLinkTest(){
        String link1 = "github.com";
        String link2 = "stackoverflow.com";

        linkRepository.add(link1, OffsetDateTime.now());
        linkRepository.add(link2, OffsetDateTime.now());

        var links = linkRepository.findAll();

        assertThat(links, is(notNullValue()));

        List<Link> list = new ArrayList<>();
        for (Link link : links) {
            list.add(link);
        }

        assertThat(list, is(not(emptyIterable())));
        assertThat(list.size(), equalTo(2));
        assertThat(list.get(0).getLink(), equalTo(link1));
        assertThat(list.get(1).getLink(), equalTo(link2));
    }
}
