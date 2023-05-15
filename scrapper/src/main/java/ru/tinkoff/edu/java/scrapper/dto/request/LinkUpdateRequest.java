package ru.tinkoff.edu.java.scrapper.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

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
