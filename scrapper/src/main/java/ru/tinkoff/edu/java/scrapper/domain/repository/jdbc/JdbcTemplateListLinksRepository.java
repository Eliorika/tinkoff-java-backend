package ru.tinkoff.edu.java.scrapper.domain.repository.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.domain.dto.Chat;
import ru.tinkoff.edu.java.scrapper.domain.dto.Link;
import ru.tinkoff.edu.java.scrapper.domain.dto.TrackLink;
import ru.tinkoff.edu.java.scrapper.domain.repository.ListLinksRepo;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;

@Repository
public class JdbcTemplateListLinksRepository implements ListLinksRepo {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<TrackLink> rowMapper;

    public JdbcTemplateListLinksRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        rowMapper = ((rs, rowNum) ->
                new TrackLink(
                        rs.getLong("track_id"),
                        new Link(
                                rs.getLong("link_id"),
                                rs.getString("link"),
                                OffsetDateTime.ofInstant(rs.getTimestamp("last_checked").toInstant(), ZoneId.of("UTC"))
                ),
                        new Chat(rs.getLong("tg_chat"))
                        ));
    }

    public List<TrackLink> findAll() {
        String sql = "select * from links_list join links using(link_id)";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public List<TrackLink> findAllByChatId(long chatId) {
        String sql = "select * from links_list join links using(link_id) where tg_chat = ?";
        return jdbcTemplate.query(sql, rowMapper, chatId);
    }

    public List<TrackLink> findAllByLinkId(long linkId) {
        String sql = "select * from links_list join links using(link_id) where link_id = ?";
        return jdbcTemplate.query(sql, rowMapper, linkId);
    }

    public void add(long chatId, long linkId) {
        String sql = """
                     insert into links_list (tg_chat, link_id) values (?,?)
                     on conflict do nothing
                     """;
        jdbcTemplate.update(sql, chatId, linkId);
    }

    public void remove(long chatId, long linkId) {
        String sql = "delete from links_list where tg_chat = ? and link_id = ?";
        jdbcTemplate.update(sql, chatId, linkId);
    }

}
