package ru.tinkoff.edu.java.bot.client.dto.response;

import lombok.Data;

import java.net.URI;
public record LinkResponse(Long id, URI url) {
}