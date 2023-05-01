package ru.tinkoff.edu.java.scrapper.service.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.bot.dto.request.LinkUpdateRequest;
import ru.tinkoff.edu.java.scrapper.configuration.ApplicationConfig;

import static org.springframework.web.servlet.view.json.MappingJackson2JsonView.DEFAULT_CONTENT_TYPE;

@Service
public class ScrapperQueueProducer {
    private final RabbitTemplate rabbitTemplate;
    private final MessageConverter jsonMessageConverter;
    private final String exchange;
    private final String queue;

    public ScrapperQueueProducer(RabbitTemplate rabbitTemplate, ApplicationConfig applicationConfig, org.springframework.amqp.support.converter.MessageConverter jsonMessageConverter) {
        this.rabbitTemplate = rabbitTemplate;
        exchange = applicationConfig.exchange();
        queue = applicationConfig.queue();
        this.jsonMessageConverter = jsonMessageConverter;
    }

    public void send(LinkUpdateRequest update) {
            rabbitTemplate.convertAndSend(exchange, queue, update);
    }
}