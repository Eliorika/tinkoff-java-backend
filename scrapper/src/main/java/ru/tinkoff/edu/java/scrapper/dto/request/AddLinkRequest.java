package ru.tinkoff.edu.java.scrapper.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.net.URI;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddLinkRequest{
    private @NonNull URI url;
}
