package ru.tinkoff.edu.java.scrapper.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.OffsetDateTime;
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class GitHubResponse {

    @JsonProperty("full_name")
    private String sUser;
    @JsonProperty("pushed_at")
    private @NonNull OffsetDateTime updatedAt;
}
