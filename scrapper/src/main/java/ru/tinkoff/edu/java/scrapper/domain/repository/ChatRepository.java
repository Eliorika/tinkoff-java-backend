package ru.tinkoff.edu.java.scrapper.domain.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.domain.dto.Chat;
import ru.tinkoff.edu.java.scrapper.domain.dto.Link;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;

@Repository
public class ChatRepository {

    private final JdbcTemplate template;
    private final RowMapper<Chat> rowMapper;

    @Autowired
    public ChatRepository(JdbcTemplate jdbcTemplate) {
        this.template = jdbcTemplate;
        rowMapper = ((rs, rowNum) -> new Chat(
                rs.getLong("tg_chat")
                ));
    }

    public List<Chat> findAll() {
        String sql = "select * from chats";
        return template.query(sql, rowMapper);
    }

    public Chat findById(Long id) {
        String sql = "select * from chats where tg_chat = ?";
        var res = template.query(sql, rowMapper, id);
        return res.size() > 0 ? res.get(0) : null;
    }

    public Chat add(Long id) {
        String sql ="""
                        insert into chats (tg_chat) values (?)
                        on conflict (tg_chat) do nothing
                        """;
        template.update(sql, id);
        return this.findById(id);
    }

    public void remove(Long id){
        String sql = """
                      delete from chats where tg_chat = ?
                      """;
        template.update(sql, id);
    }
}
