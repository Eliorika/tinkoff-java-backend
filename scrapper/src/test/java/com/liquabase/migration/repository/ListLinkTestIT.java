package com.liquabase.migration.repository;

import com.liquabase.migration.IntegrationEnvironment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.domain.dto.Chat;
import ru.tinkoff.edu.java.scrapper.domain.dto.Link;
import ru.tinkoff.edu.java.scrapper.domain.dto.TrackLink;
import ru.tinkoff.edu.java.scrapper.domain.repository.ChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.repository.LinkRepository;
import ru.tinkoff.edu.java.scrapper.domain.repository.ListLinksRepository;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@SpringBootTest(classes = {IntegrationEnvironment.EnvironmentConfig.class,
        ChatRepository.class, ListLinksRepository.class, LinkRepository.class})
public class ListLinkTestIT extends IntegrationEnvironment {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private ListLinksRepository listLinksRepository;

    @Test
    @Transactional
    @Rollback
    public void addTrackLinkTest(){

        Long chatID = (long)123;
        chatRepository.add(chatID);

        String linkUrl = "github.com";
        Link link = linkRepository.add(linkUrl, OffsetDateTime.now());

        listLinksRepository.add(chatID, link.getId());

        var list = listLinksRepository.findAll();

        assertThat(list, is(notNullValue()));
        assertThat(list, is(not(emptyIterable())));
        assertThat(list.size(), equalTo(1));
        assertThat(list.get(0).getTg_chat().getTg_chat(), equalTo(chatID));
        assertThat(list.get(0).getLink().getLink().toString(), equalTo(linkUrl));
    }

    @Test
    @Transactional
    @Rollback
    public void removeTrackLinkTest(){
        Long chatID = (long)123;
        chatRepository.add(chatID);

        String linkUrl1 = "github.com";
        Link link1 = linkRepository.add(linkUrl1, OffsetDateTime.now());

        String linkUrl2 = "something.com";
        Link link2 = linkRepository.add(linkUrl2, OffsetDateTime.now());

        listLinksRepository.add(chatID, link1.getId());
        listLinksRepository.add(chatID, link2.getId());

        listLinksRepository.remove(chatID, link1.getId());

        var list = listLinksRepository.findAll();

        assertThat(list, is(notNullValue()));
        assertThat(list, is(not(emptyIterable())));
        assertThat(list.size(), equalTo(1));
        assertThat(list.get(0).getTg_chat().getTg_chat(), equalTo(chatID));
        assertThat(list.get(0).getLink().getLink().toString(), equalTo(linkUrl2));
    }

    @Test
    @Transactional
    @Rollback
    public void findChatTest(){
        Long chatID = (long)123;
        chatRepository.add(chatID);

        String linkUrl1 = "github.com";
        Link link1 = linkRepository.add(linkUrl1, OffsetDateTime.now());

        String linkUrl2 = "something.com";
        Link link2 = linkRepository.add(linkUrl2, OffsetDateTime.now());

        listLinksRepository.add(chatID, link1.getId());
        listLinksRepository.add(chatID, link2.getId());


        var list = listLinksRepository.findAll();

        assertThat(list, is(notNullValue()));
        assertThat(list, is(not(emptyIterable())));
        assertThat(list.size(), equalTo(2));

        assertThat(list.get(0).getTg_chat().getTg_chat(), equalTo(chatID));
        assertThat(list.get(0).getLink().getLink().toString(), equalTo(linkUrl1));

        assertThat(list.get(1).getTg_chat().getTg_chat(), equalTo(chatID));
        assertThat(list.get(1).getLink().getLink().toString(), equalTo(linkUrl2));
    }
}
