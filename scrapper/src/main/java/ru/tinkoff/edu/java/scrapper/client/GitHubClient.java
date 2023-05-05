package ru.tinkoff.edu.java.scrapper.client;

import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.scrapper.dto.response.GitHubResponse;

import java.time.OffsetDateTime;

public class GitHubClient {

    @Value("${github.token}")
    private String token;
    private final WebClient webClient;

    public GitHubClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public GitHubResponse fetchRepository(String sUser, String sRepo) {
         GitHubResponse response;
         String auth = "Bearer " + token;
        try {
            JSONObject result = new JSONObject( webClient.get()
                    .uri("/repos/{owner}/{repo}", sUser, sRepo)
                    .header("Authorization", auth)
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

