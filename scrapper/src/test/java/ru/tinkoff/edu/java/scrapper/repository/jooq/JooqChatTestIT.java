package ru.tinkoff.edu.java.scrapper.repository.jooq;

import ru.tinkoff.edu.java.scrapper.repository.IntegrationEnvironment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.domain.repository.jooq.JooqChatRepository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@SpringBootTest(classes = {IntegrationEnvironment.EnvironmentConfig.class, JooqChatRepository.class})
public class JooqChatTestIT extends IntegrationEnvironment {

    @Autowired
    private JooqChatRepository chatRepository;

    @Test
    @Transactional
    @Rollback
    public void addChatTest(){
        runMigrations(IntegrationEnvironment.CONTAINER);
        Long chatID = (long)123;

        chatRepository.add(chatID);

        var list = chatRepository.findAll();


        assertThat(list, is(not(emptyIterable())));
        assertThat(list.size(), equalTo(1));
        assertThat(list.get(0).getTg_chat(), equalTo(chatID));
    }

    @Test
    @Transactional
    @Rollback
    public void removeChatTest(){
        Long chatID1 = (long)123;
        Long chatID2 = (long) 133;

        chatRepository.add(chatID1);
        chatRepository.add(chatID2);
        chatRepository.remove(chatID1);

        var list = chatRepository.findAll();


        assertThat(list, is(not(emptyIterable())));
        assertThat(list.size(), equalTo(1));
        assertThat(list.get(0).getTg_chat(), equalTo(chatID2));
    }

    @Test
    @Transactional
    @Rollback
    public void findChatTest(){
        Long chatID1 = (long)123;
        Long chatID2 = (long) 133;

        chatRepository.add(chatID1);
        chatRepository.add(chatID2);

        var list = chatRepository.findAll();

        assertThat(list, is(not(emptyIterable())));
        assertThat(list.size(), equalTo(2));
        assertThat(list.get(0).getTg_chat(), equalTo(chatID1));
        assertThat(list.get(1).getTg_chat(), equalTo(chatID2));
    }
}
