package ru.tinkoff.edu.java.scrapper.configuration;

import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;
import ru.tinkoff.edu.java.scrapper.scheduler.Scheduler;


import javax.sql.DataSource;
import java.time.Duration;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(@NotNull String test,
                                @NotNull Scheduler scheduler,
                                @NotNull AccessType databaseAccessType,
                                @NotNull String queue,
                                @NotNull String exchange,
                                @NotNull boolean useQueue) {
    @Value("${app.scheduler.interval}")
    private static Duration schedulerTime;


}


