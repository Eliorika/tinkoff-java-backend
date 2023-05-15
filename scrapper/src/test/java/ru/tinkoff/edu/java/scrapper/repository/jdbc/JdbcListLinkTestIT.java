package ru.tinkoff.edu.java.scrapper.repository.jdbc;

import ru.tinkoff.edu.java.scrapper.repository.IntegrationEnvironment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.domain.dto.Link;
import ru.tinkoff.edu.java.scrapper.domain.repository.jdbc.JdbcTemplateChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.repository.jdbc.JdbcTemplateLinkRepository;
import ru.tinkoff.edu.java.scrapper.domain.repository.jdbc.JdbcTemplateListLinksRepository;

import java.time.OffsetDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@SpringBootTest(classes = {IntegrationEnvironment.EnvironmentConfig.class,
        JdbcTemplateChatRepository.class, JdbcTemplateListLinksRepository.class, JdbcTemplateLinkRepository.class})
public class JdbcListLinkTestIT extends IntegrationEnvironment {

    @Autowired
    private JdbcTemplateChatRepository chatRepository;

    @Autowired
    private JdbcTemplateLinkRepository linkRepository;

    @Autowired
    private JdbcTemplateListLinksRepository listLinksRepository;

    @Test
    @Transactional
    @Rollback
    public void addTrackLinkTest(){

        Long chatID = (long)123;
        chatRepository.add(chatID);

        String linkUrl = "github.com";
        Link link = linkRepository.add(linkUrl, OffsetDateTime.now());

        listLinksRepository.add(chatID, link.getLink_id());

        var list = listLinksRepository.findAll();

        assertThat(list, is(notNullValue()));
        assertThat(list, is(not(emptyIterable())));
        assertThat(list.size(), equalTo(1));
        assertThat(list.get(0).getTg_chat().getTgChat(), equalTo(chatID));
        assertThat(list.get(0).getLink().getLink().toString(), equalTo(linkUrl));
    }

    @Test
    @Transactional
    @Rollback
    public void removeTrackLinkTest(){
        long chatID = (long)123;
        chatRepository.add(chatID);

        String linkUrl1 = "github.com";
        Link link1 = linkRepository.add(linkUrl1, OffsetDateTime.now());

        String linkUrl2 = "something.com";
        Link link2 = linkRepository.add(linkUrl2, OffsetDateTime.now());

        listLinksRepository.add(chatID, link1.getLink_id());
        listLinksRepository.add(chatID, link2.getLink_id());

        listLinksRepository.remove(chatID, link1.getLink_id());

        var list = listLinksRepository.findAll();

        assertThat(list, is(notNullValue()));
        assertThat(list, is(not(emptyIterable())));
        assertThat(list.size(), equalTo(1));
        assertThat(list.get(0).getTg_chat().getTgChat(), equalTo(chatID));
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

        listLinksRepository.add(chatID, link1.getLink_id());
        listLinksRepository.add(chatID, link2.getLink_id());


        var list = listLinksRepository.findAll();

        assertThat(list, is(notNullValue()));
        assertThat(list, is(not(emptyIterable())));
        assertThat(list.size(), equalTo(2));

        assertThat(list.get(0).getTg_chat().getTgChat(), equalTo(chatID));
        assertThat(list.get(0).getLink().getLink().toString(), equalTo(linkUrl1));

        assertThat(list.get(1).getTg_chat().getTgChat(), equalTo(chatID));
        assertThat(list.get(1).getLink().getLink().toString(), equalTo(linkUrl2));
    }
}
