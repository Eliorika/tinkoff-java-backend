package ru.tinkoff.edu.java.bot.dto.request;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkUpdateRequest {
    private long id;
    private @NonNull String url;
    private @NonNull String description;
    private List<Long> tgChatIds;

}
