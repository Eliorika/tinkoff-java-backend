package ru.tinkoff.edu.java.bot;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.tinkoff.edu.java.bot.commands.Command;

import java.util.HashSet;
import java.util.Set;

@Component
public class Bot extends TelegramLongPollingBot {
    @Value("${telegram.bot-name}")
    private String botName;

    @Value("${telegram.bot-token}")
    private String token;

    private static final Logger log = Logger.getLogger(Bot.class);
    private static final Set<Command> botCommant = new HashSet<>();




    public final String getBotUsername() {
        return this.botName;
    }


    public final String getBotToken() {
        return this.token;
    }


    @Override
    public void onUpdateReceived(Update update) {
        log.debug("update received");
        if(update.hasMessage()&& update.getMessage().hasText()) {
            SendMessage sm = UpdateProcessor.process(update);
            try {
                execute(sm);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }




}
