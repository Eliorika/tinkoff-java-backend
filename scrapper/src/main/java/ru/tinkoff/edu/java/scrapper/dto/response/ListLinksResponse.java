package ru.tinkoff.edu.java.scrapper.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import ru.tinkoff.edu.java.scrapper.domain.dto.Link;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
public class ListLinksResponse {
    public List<LinkResponse> links;
    public int size;

    public static ListLinksResponse fromLinkListToResponse(Collection<Link> links) {
        List<LinkResponse> linkArrayList = new ArrayList<>();
        for (Link link : links) {
            linkArrayList.add(new LinkResponse(link.getId(), link.getLink()));
        }
        return new ListLinksResponse(linkArrayList, linkArrayList.size());
    }



}
