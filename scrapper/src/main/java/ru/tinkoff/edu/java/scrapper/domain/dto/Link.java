package ru.tinkoff.edu.java.scrapper.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.OffsetDateTime;

@AllArgsConstructor
@Getter
public class Link {
    private long link_id;
    private URI link;
    private OffsetDateTime lastUpdated;

    public Link(long id, String link, OffsetDateTime update){
        this.link_id = id;
        try {
            this.link = new URI(link);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        this.lastUpdated = update;
    }
}
