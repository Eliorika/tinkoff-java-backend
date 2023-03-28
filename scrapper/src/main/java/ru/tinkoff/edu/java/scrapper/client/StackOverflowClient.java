package ru.tinkoff.edu.java.scrapper.client;

import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.scrapper.dto.response.StackOverflowResponse;

public class StackOverflowClient {

    private final WebClient webClient;

    public StackOverflowClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public StackOverflowResponse fetchQuestion(Long questionId) {
        return webClient.get()
                .uri("/questions/{id}?site=stackoverflow", questionId)
                .retrieve()
                .bodyToMono(StackOverflowResponse.class)
                .block();
    }
}
