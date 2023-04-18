package ru.tinkoff.edu.java.scrapper.domain.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.domain.dto.Link;
import ru.tinkoff.edu.java.scrapper.domain.repository.LinkRepo;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;

@Repository
public class JdbcTemplateLinkRepository implements LinkRepo {

    private final JdbcTemplate template;
    private final RowMapper<Link> rowMapper;

    @Autowired
    public JdbcTemplateLinkRepository(JdbcTemplate jdbcTemplate) {
        this.template = jdbcTemplate;
        rowMapper = ((rs, rowNum) -> new Link(
                rs.getLong("link_id"),
                rs.getString("link"),
                OffsetDateTime.ofInstant(rs.getTimestamp("last_checked").toInstant(), ZoneId.of("UTC"))
        ));
    }

    public List<Link> findAll() {
        String sql = "select * from links";
        return template.query(sql, rowMapper);
    }

    public List<Link> findAllByChat(long chat_id){
        String sql = """
                        select * from links_list join links l on l.link_id = links_list.link_id
                        where tg_chat = ?""";
        return template.query(sql, rowMapper, chat_id);
    }

    public Link findByUrl(String url) {
        String sql = "select * from links where link = ?";
        var res = template.query(sql, rowMapper, url);
        return res.size() > 0 ? res.get(0) : null;
    }

    public Link add(String link, OffsetDateTime update) {
        String sql ="""
                        insert into links (link, last_checked) values (?, ?)
                        on conflict (link) do nothing
                        """;
        template.update(sql, link, update);
        return this.findByUrl(link);
    }

    public void remove(long id){
        String sql = """
                      delete from links where link_id = ?
                      """;
        template.update(sql, id);
    }

    public void update(long id){
        String sql = """
                     update links set last_checked = now() where link_id = ?
                     """;
        template.update(sql, id);
    }

    public List<Link> findOld() {
        String sql = "select * from links where links.last_checked < now() - interval '5 minutes'";
        return template.query(sql, rowMapper);
    }
}
