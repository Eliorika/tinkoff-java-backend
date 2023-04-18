package ru.tinkoff.edu.java.scrapper.domain.repository.jooq;

import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.domain.dto.Chat;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.pojos.Chats;
import ru.tinkoff.edu.java.scrapper.domain.jooq.Tables;
import ru.tinkoff.edu.java.scrapper.domain.repository.ChatRepo;

import java.util.ArrayList;
import java.util.List;

import static ru.tinkoff.edu.java.scrapper.domain.jooq.Tables.CHATS;


@Repository
@AllArgsConstructor
public class JooqChatRepository implements ChatRepo {

    private DSLContext dslContext;

    @Override
    public List<Chat> findAll() {
        List<Chats> list = dslContext.select().from(CHATS).fetchInto(Chats.class);
        return fromListChats(list);
    }

    @Override
    public Chat findById(Long id) {
        var res = dslContext.select(CHATS.fields()).from(CHATS).
                where(CHATS.TG_CHAT.eq(id)).fetchInto(Chats.class);
        return res.size() == 0 ? null : fromListChats(res).get(0);
    }

    @Override
    public Chat add(Long id) {
        dslContext.insertInto(CHATS).set(CHATS.TG_CHAT, id).execute();
        return findById(id);
    }

    @Override
    public void remove(Long id) {
        dslContext.deleteFrom(CHATS).where(CHATS.TG_CHAT.eq(id)).execute();
    }

    static List<Chat> fromListChats(List<Chats> list){
        List<Chat> chats = new ArrayList<>();
        for(Chats chat: list){
            chats.add(new Chat(chat.getTgChat()));
        }
        return chats;
    }

}
