package ru.tinkoff.edu.java.scrapper.service.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.configuration.ApplicationConfig;
import ru.tinkoff.edu.java.scrapper.dto.request.LinkUpdateRequest;

@Service
public class ScrapperQueueProducer {
    private final RabbitTemplate rabbitTemplate;
    private final String exchange;
    private final String queue;

    public ScrapperQueueProducer(RabbitTemplate rabbitTemplate, ApplicationConfig applicationConfig) {
        this.rabbitTemplate = rabbitTemplate;
        exchange = applicationConfig.exchange();
        queue = applicationConfig.queue();
    }

    public void send(LinkUpdateRequest update) {
            rabbitTemplate.convertAndSend(exchange, queue, update);
    }
}
