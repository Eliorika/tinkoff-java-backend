package ru.tinkoff.edu.java.scrapper.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class LinkResponse{
    private @NonNull Long id;
    private @NonNull String url;
}
