package ru.tinkoff.edu.java.scrapper.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
@AllArgsConstructor
public class ListLinksResponse {
    public List<LinkResponse> links;
    public int size;
}
