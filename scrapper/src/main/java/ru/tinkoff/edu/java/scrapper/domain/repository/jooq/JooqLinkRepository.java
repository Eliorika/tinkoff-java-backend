package ru.tinkoff.edu.java.scrapper.domain.repository.jooq;

import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.domain.dto.Chat;
import ru.tinkoff.edu.java.scrapper.domain.dto.Link;
import ru.tinkoff.edu.java.scrapper.domain.jooq.Tables;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.pojos.Chats;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.pojos.Links;
import ru.tinkoff.edu.java.scrapper.domain.repository.LinkRepo;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import static ru.tinkoff.edu.java.scrapper.domain.jooq.Tables.LINKS;
import static ru.tinkoff.edu.java.scrapper.domain.jooq.Tables.LINKS_LIST;


@Repository
@AllArgsConstructor
public class JooqLinkRepository implements LinkRepo {

    private DSLContext dslContext;
    @Override
    public List<Link> findAll() {
        var list = dslContext.select().from(LINKS).fetchInto(Links.class);
        return fromListJLinks(list);
    }

    @Override
    public List<Link> findAllByChat(long chat_id) {
        var list = dslContext.select().from(LINKS).join(LINKS_LIST).using(LINKS.LINK_ID)
                .where(LINKS_LIST.TG_CHAT.eq(chat_id)).fetchInto(Links.class);
        return fromListJLinks(list);
    }

    @Override
    public Link findByUrl(String url) {
        var res = dslContext.select().from(LINKS)
                .where(LINKS.LINK.eq(url)).fetchInto(Links.class);
        return res.size() == 0 ? null : fromListJLinks(res).get(0);
    }

    @Override
    public Link add(String link, OffsetDateTime update) {
        dslContext.insertInto(LINKS).set(LINKS.LINK, link).set(LINKS.LAST_CHECKED, update.toLocalDateTime()).execute();
        return findByUrl(link);
    }

    @Override
    public void remove(long id) {
        dslContext.deleteFrom(LINKS).where(LINKS.LINK_ID.eq(id)).execute();
    }

    @Override
    public void update(long id) {
        dslContext.update(LINKS).set(LINKS.LAST_CHECKED, LocalDateTime.now()).where(LINKS.LINK_ID.eq(id)).execute();
    }

    @Override
    public List<Link> findOld() {
        return findAll().stream().filter(link ->
                OffsetDateTime.now().toEpochSecond() - link.getLastUpdated().toEpochSecond() > 300).toList();
    }

    static List<Link> fromListJLinks(List<Links> list){
        List<Link> links = new ArrayList<>();
        for(Links link: list){
            links.add(fromJLinks(link));
        }
        return links;
    }

    static Link fromJLinks(Links link){
        return new Link(link.getLinkId(), link.getLink(), link.getLastChecked().atOffset(ZoneOffset.ofHours(3)));
    }
}
