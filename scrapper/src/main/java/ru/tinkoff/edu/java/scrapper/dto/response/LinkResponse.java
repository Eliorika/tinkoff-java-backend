package ru.tinkoff.edu.java.scrapper.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import ru.tinkoff.edu.java.scrapper.domain.dto.Link;

import java.net.URI;

@Data
@AllArgsConstructor
public class LinkResponse{
    private @NonNull Long id;
    private @NonNull URI url;

    public static LinkResponse fromLinkToLinkResponse(Link link) {
        return new LinkResponse(link.getId(), link.getLink());
    }
}
