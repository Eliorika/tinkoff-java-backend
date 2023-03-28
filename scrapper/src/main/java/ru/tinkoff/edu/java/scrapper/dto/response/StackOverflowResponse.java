package ru.tinkoff.edu.java.scrapper.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
public class StackOverflowResponse {
    private String title;
    @JsonProperty("updated_at")
    private @NonNull OffsetDateTime updatedAt;
}
