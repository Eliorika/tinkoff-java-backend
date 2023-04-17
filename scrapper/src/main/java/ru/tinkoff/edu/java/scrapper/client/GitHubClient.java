package ru.tinkoff.edu.java.scrapper.client;

import netscape.javascript.JSObject;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.scrapper.dto.response.GitHubResponse;

import java.time.OffsetDateTime;

public class GitHubClient {

    private final WebClient webClient;

    public GitHubClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public GitHubResponse fetchRepository(String sUser, String sRepo) {
         GitHubResponse response;
        try {
            JSONObject result = new JSONObject( webClient.get()
                    .uri("/repos/{owner}/{repo}", sUser, sRepo)
                    .header("Authorization", "Bearer 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")
                    .retrieve()
                    .bodyToMono(String.class)
                    .block());
            String user = result.getString("full_name");
            OffsetDateTime update = OffsetDateTime.parse(result.getString("pushed_at"));
            response = new GitHubResponse(user,update);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return response;
    }
}

