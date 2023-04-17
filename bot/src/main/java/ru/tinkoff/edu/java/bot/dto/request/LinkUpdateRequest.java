package ru.tinkoff.edu.java.bot.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
@AllArgsConstructor
public class LinkUpdateRequest {
    private long id;
    private @NonNull String url;
    private @NonNull String description;
    private List<Long> tgChatIds;
}
