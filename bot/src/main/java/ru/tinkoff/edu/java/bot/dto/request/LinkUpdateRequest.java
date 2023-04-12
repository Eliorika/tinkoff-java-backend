package ru.tinkoff.edu.java.bot.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
@Data
@AllArgsConstructor
public class LinkUpdateRequest {
    private int id;
    private @NonNull String url;
    private @NonNull String description;
    private long[] tgChatIds;
}
