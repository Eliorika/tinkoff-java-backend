package ru.tinkoff.edu.java.scrapper.client;

import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.scrapper.dto.response.StackOverflowResponse;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

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
                    .block())
                    .getJSONArray("items").getJSONObject(0);
            var questions = getAnswersTime(questionId);
            var comments = getCommentsTime(questionId);
            return new StackOverflowResponse(result.getString("question_id"),
                fromLongToOffsetDateTime(result.getLong("last_activity_date")),
                    questions,
                    comments);
        } catch (JSONException e) {
            return null;
        }
    }
    private List<OffsetDateTime> getAnswersTime(long id) {
        try {
            JSONArray answers = new JSONObject(webClient.get()
                    .uri("/questions/{id}/answers?site=stackoverflow", id).
                    retrieve()
                    .bodyToMono(String.class)
                    .block())
                    .getJSONArray("items");
            List<OffsetDateTime> list = new ArrayList<>();
            for (int i = 0; i < answers.length(); i++) {
                list.add(fromLongToOffsetDateTime(answers.getJSONObject(i).getLong("creation_date")));
            }
            return list;
        } catch (JSONException e) {
            return null;
        }
    }

    private List<OffsetDateTime> getCommentsTime(long id) {
        try {
            JSONArray answers = new JSONObject(webClient.get()
                    .uri("/questions/{id}/comments?site=stackoverflow", id).
                    retrieve()
                    .bodyToMono(String.class)
                    .block())
                    .getJSONArray("items");
            List<OffsetDateTime> list = new ArrayList<>();
            for (int i = 0; i < answers.length(); i++) {
                list.add(fromLongToOffsetDateTime(answers.getJSONObject(i).getLong("creation_date")));
            }
            return list;
        } catch (JSONException e) {
            return null;
        }
    }

    private OffsetDateTime fromLongToOffsetDateTime(Long date){
        return OffsetDateTime.of(LocalDateTime.ofEpochSecond(date, 0, ZoneOffset.UTC), ZoneOffset.UTC);
    }
}
