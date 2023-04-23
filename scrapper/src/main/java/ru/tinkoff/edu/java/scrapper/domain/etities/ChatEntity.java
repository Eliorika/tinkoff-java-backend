package ru.tinkoff.edu.java.scrapper.domain.etities;

import jakarta.persistence.*;
import lombok.Data;
import ru.tinkoff.edu.java.scrapper.domain.dto.Chat;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "chats")
public class ChatEntity {
    @Id
    @Column(name="tg_chat")
    private Long chat;

    @ManyToMany
    @JoinTable(name = "links_list",
            joinColumns = @JoinColumn(name = "tg_chat"),
            inverseJoinColumns = @JoinColumn(name = "link_id"))
    private Set<LinkEntity> links = new HashSet<>();
}
