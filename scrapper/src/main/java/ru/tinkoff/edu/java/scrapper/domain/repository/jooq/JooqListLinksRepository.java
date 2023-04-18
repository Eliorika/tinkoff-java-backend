package ru.tinkoff.edu.java.scrapper.domain.repository.jooq;

import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record5;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.domain.dto.Chat;
import ru.tinkoff.edu.java.scrapper.domain.dto.Link;
import ru.tinkoff.edu.java.scrapper.domain.dto.TrackLink;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.pojos.Links;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.pojos.LinksList;
import ru.tinkoff.edu.java.scrapper.domain.repository.ListLinksRepo;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import static ru.tinkoff.edu.java.scrapper.domain.jooq.Tables.LINKS;
import static ru.tinkoff.edu.java.scrapper.domain.jooq.tables.LinksList.LINKS_LIST;

@Repository
@AllArgsConstructor
public class JooqListLinksRepository implements ListLinksRepo {

    private DSLContext dslContext;

    @Override
    public List<TrackLink> findAll() {
        var list = dslContext.select(LINKS_LIST.TG_CHAT, LINKS_LIST.LINK_ID, LINKS_LIST.TRACK_ID, LINKS.LAST_CHECKED,LINKS.LINK)
                .from(LINKS_LIST).join(LINKS).using(LINKS.LINK_ID).fetch();
        return list.stream().map(this::fromRecord).toList();
    }

    @Override
    public List<TrackLink> findAllByChatId(long chatId) {
        var list = dslContext.select(LINKS_LIST.TG_CHAT, LINKS_LIST.LINK_ID, LINKS_LIST.TRACK_ID, LINKS.LAST_CHECKED,LINKS.LINK)
                .from(LINKS_LIST).join(LINKS).using(LINKS.LINK_ID).where(LINKS_LIST.TG_CHAT.eq(chatId)).fetch();
        return list.stream().map(this::fromRecord).toList();
    }

    @Override
    public List<TrackLink> findAllByLinkId(long linkId) {
        var list = dslContext.select(LINKS_LIST.TG_CHAT, LINKS_LIST.LINK_ID, LINKS_LIST.TRACK_ID, LINKS.LAST_CHECKED,LINKS.LINK)
                .from(LINKS_LIST).join(LINKS).using(LINKS.LINK_ID).where(LINKS_LIST.LINK_ID.eq(linkId)).fetch();
        return list.stream().map(this::fromRecord).toList();
    }

    @Override
    public void add(long chat_id, long linkId) {
        dslContext.insertInto(LINKS_LIST).set(LINKS_LIST.TG_CHAT, chat_id).
                set(LINKS_LIST.LINK_ID, linkId).execute();
    }

    @Override
    public void remove(long chat_id, long link_id) {
        dslContext.deleteFrom(LINKS_LIST)
                .where(LINKS_LIST.LINK_ID.eq(link_id)).and( LINKS_LIST.TG_CHAT.eq(chat_id))
                .execute();
    }


    private TrackLink fromRecord(Record5 record5){
        return new TrackLink((long) record5.get("TRACK_ID"),
                            new Link((long) record5.get("LINK_ID"), (String) record5.get("LINK"),
                                    ((LocalDateTime) record5.get("LAST_CHECKED")).atOffset(ZoneOffset.ofHours(3))),
                            new Chat((long) record5.get("TG_CHAT")));

    }


}
