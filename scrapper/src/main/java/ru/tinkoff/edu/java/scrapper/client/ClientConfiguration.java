package ru.tinkoff.edu.java.scrapper.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfiguration {

    @Bean
    public WebClient githubWebClient() {
        return WebClient.builder()
                .baseUrl("https://api.github.com")
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean
    public GitHubClient githubClient(WebClient githubWebClient) {
        return new GitHubClient(githubWebClient);
    }

    @Bean
    public WebClient stackOverflowWebClient() {
        return WebClient.builder()
                .baseUrl("https://api.stackexchange.com")
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean
    public StackOverflowClient stackOverflowClient(WebClient stackOverflowWebClient) {
        return new StackOverflowClient(stackOverflowWebClient);
    }

    @Bean
    public BotClient botClient(){
        return new BotClient(botWebClient());
    }

    @Bean
    public WebClient botWebClient(){
        return WebClient.builder()
                .baseUrl("http://localhost:8081")
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
