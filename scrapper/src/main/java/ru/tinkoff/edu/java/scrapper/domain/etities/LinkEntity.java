package ru.tinkoff.edu.java.scrapper.domain.etities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
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



}
