package ru.tinkoff.edu.java.scrapper.client;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.scrapper.dto.response.StackOverflowResponse;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class StackOverflowClient {

    private final WebClient webClient;

    public StackOverflowClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public StackOverflowResponse fetchQuestion(Long questionId) {
        try {
            JSONObject result = new JSONObject(webClient.get()
                    .uri("/questions/{id}?site=stackoverflow", questionId)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block()).
                    getJSONArray("items").getJSONObject(0);
            return new StackOverflowResponse(result.getString("question_id"),
                    OffsetDateTime.of(LocalDateTime.ofEpochSecond(result.getLong("last_activity_date"),
                            0, ZoneOffset.UTC), ZoneOffset.UTC));
        } catch (JSONException e) {
            return null;
        }
    }
}
