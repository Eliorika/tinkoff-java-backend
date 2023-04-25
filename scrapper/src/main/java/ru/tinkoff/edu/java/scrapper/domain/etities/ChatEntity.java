package ru.tinkoff.edu.java.scrapper.domain.etities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ru.tinkoff.edu.java.scrapper.domain.dto.Chat;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatEntity chat = (ChatEntity) o;
        return this.chat == chat.chat;
    }

}
