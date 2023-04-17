package ru.tinkoff.edu.java.scrapper.client;


import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.bot.dto.request.LinkUpdateRequest;


public class BotClient {
    private final WebClient webClient;

    public BotClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public void update(LinkUpdateRequest linkUpdate) {
        webClient.post()
                .uri("/updates")
                .bodyValue(linkUpdate)
                .retrieve().
                toBodilessEntity()
                .block();
    }
}
