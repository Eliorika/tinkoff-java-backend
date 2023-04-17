package ru.tinkoff.edu.java.scrapper.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.OffsetDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Chat {
    private long tg_chat;
}
