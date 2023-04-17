package ru.tinkoff.edu.java.bot;


import jakarta.annotation.PostConstruct;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
public class Bot extends TelegramLongPollingBot {
    @Value("${telegram.bot-name}")
    private String botName;

    @Value("${telegram.bot-token}")
    private String token;
    private final UpdateProcessor updateProcessor;

    private static final Logger log = Logger.getLogger(Bot.class);

    public Bot(UpdateProcessor updateProcessor) {
        this.updateProcessor = updateProcessor;
    }


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
            SendMessage sm = updateProcessor.process(update);
            try {
                execute(sm);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(this);
    }

    public void sendUpdateMessage(SendMessage sm){
        try {
            execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }



}
