package ru.tinkoff.edu.java.bot.configuration;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app-bot", ignoreUnknownFields = false)
public record ApplicationConfig(@NotNull String test,
                                @NotNull String queue,
                                @NotNull String exchange) {

}
