package ru.tinkoff.edu.java.scrapper.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TrackLink {
    private long id;
    private Link link;
    private Chat tg_chat;

}
