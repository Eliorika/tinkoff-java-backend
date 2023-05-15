package ru.tinkoff.edu.java.bot.configuration;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public Counter rabbitCounter(MeterRegistry prometheusMeterRegistry) {
        return Counter.builder("rabbitmq.messages.processed")
            .description("Number of processed RabbitMQ messages")
            .register(prometheusMeterRegistry);
    }
}
