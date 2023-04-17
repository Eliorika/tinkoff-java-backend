package ru.tinkoff.edu.java.scrapper.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class StackOverflowResponse {
    @JsonProperty("question_id")
    private String title;
    @JsonProperty("last_activity_date")
    private @NonNull OffsetDateTime updatedAt;
}
