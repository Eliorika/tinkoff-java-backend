package ru.tinkoff.edu.java.scrapper.client;

import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.scrapper.dto.response.GithubResponse;

public class GithubClient {

    private final WebClient webClient;

    public GithubClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public GithubResponse fetchRepository(String sUser, String sRepo) {
        return webClient.get()
                .uri("/repos/{owner}/{repo}", sUser, sRepo)
                .retrieve()
                .bodyToMono(GithubResponse.class)
                .block();
    }
}

