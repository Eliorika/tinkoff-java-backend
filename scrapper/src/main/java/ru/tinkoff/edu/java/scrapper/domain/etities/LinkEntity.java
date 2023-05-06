package ru.tinkoff.edu.java.scrapper.domain.etities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name="links")
public class LinkEntity {
    @Id
    @Column(name = "link_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long link_id;

    @Column(name = "link")
    private String link;

    @Column(name = "last_checked")
    private OffsetDateTime lastUpdated;

    @ManyToMany(mappedBy = "links", fetch = FetchType.EAGER)
    private Set<ChatEntity> chats = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LinkEntity link = (LinkEntity) o;
        return this.link_id == link.link_id && Objects.equals(this.link, link.link) && Objects.equals(this.lastUpdated, link.lastUpdated);
    }

}
