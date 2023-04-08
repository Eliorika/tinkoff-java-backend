package ru.tinkoff.edu.java.bot.client;


import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.bot.client.dto.request.AddLinkRequest;
import ru.tinkoff.edu.java.bot.client.dto.request.RemoveLinkRequest;
import ru.tinkoff.edu.java.bot.client.dto.response.ListLinksResponse;
import ru.tinkoff.edu.java.bot.dto.request.LinkUpdateRequest;
import ru.tinkoff.edu.java.bot.client.dto.response.LinkResponse;

@Component
public class ScrapperClient {
    private final WebClient scrapperWebClient;


    @Autowired
    public ScrapperClient(WebClient scrapperWebClient) {
        this.scrapperWebClient = scrapperWebClient;
    }



    public ScrapperClient(String baseUrl) {
        scrapperWebClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public void registerChat(long id) {
        scrapperWebClient.post()
                .uri("/tg-chat/{id}", id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public void deleteChat(long id) {
        scrapperWebClient.delete()
                .uri("/tg-chat/{id}", id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public ListLinksResponse getLinks(long id) {
        return scrapperWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .pathSegment("links")
                        .build())
                .header("Tg-Chat-Id", Long.toString( id))
                .retrieve()
                .bodyToMono(ListLinksResponse.class)
                .block();
    }

    public LinkResponse addLink(long id, AddLinkRequest request) {
        return scrapperWebClient.post()
                .uri(uriBuilder -> uriBuilder
                        .pathSegment("links")
                        .build())
                .header("Tg-Chat-Id", Long.toString( id))
                .body(BodyInserters.fromValue(request))
                .retrieve()
                .bodyToMono(LinkResponse.class)
                .block();
    }

    public LinkResponse removeLink(long id, RemoveLinkRequest request) {
        return scrapperWebClient.method(HttpMethod.DELETE)
                .uri(uriBuilder -> uriBuilder
                        .pathSegment("links")
                        .build())
                .body(BodyInserters.fromValue(request))
                .header("Tg-Chat-Id", Long.toString( id))
                .retrieve()
                .bodyToMono(LinkResponse.class)
                .block();
    }

}
