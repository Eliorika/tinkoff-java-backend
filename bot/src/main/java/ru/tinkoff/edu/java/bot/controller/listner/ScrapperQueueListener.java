package ru.tinkoff.edu.java.bot.controller.listner;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.bot.controller.BotController;
import ru.tinkoff.edu.java.bot.dto.request.LinkUpdateRequest;


@Service
@RequiredArgsConstructor
@RabbitListener(queues = "${app-bot.queue}")
public class ScrapperQueueListener {
    private final BotController botController;
    //private final MessageConverter messageConverter;

    @RabbitHandler
    public void listen(LinkUpdateRequest update) {
        //throw new RuntimeException();
        botController.update(update);
    }
}
