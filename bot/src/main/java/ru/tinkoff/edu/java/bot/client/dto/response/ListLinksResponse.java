package ru.tinkoff.edu.java.bot.client.dto.response;

import ru.tinkoff.edu.java.bot.client.dto.response.LinkResponse;

import java.util.List;

public record ListLinksResponse(List<LinkResponse> links, int size) {
}
