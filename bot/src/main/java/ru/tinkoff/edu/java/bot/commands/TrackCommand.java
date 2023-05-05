package ru.tinkoff.edu.java.bot.commands;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.tinkoff.edu.java.bot.client.ScrapperClient;
import ru.tinkoff.edu.java.bot.client.dto.request.AddLinkRequest;

import java.net.URI;
import java.net.URISyntaxException;

@RequiredArgsConstructor
public class TrackCommand implements Command{

    private final ScrapperClient scrapperClient;
    @Override
    public String command() {
        return "/track";
    }

    @Override
    public String about() {
        return "add link to a tracking list. Format: \"/track [link]\"";
    }

    @Override
    public SendMessage handleCommand(Update update) {
        long chatId = update.getMessage().getChatId();
        String message = update.getMessage().getText();
        String link = message.substring(command().length()).trim();
        try {
            scrapperClient.addLink(chatId, new AddLinkRequest(new URI(link)));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return new SendMessage(Long.toString(chatId), "Start tracking " + link);
    }

    @Override
    public boolean supports(Update update) {
        String message = update.getMessage().getText();
        String[] msg = message.split(" ");
        return msg.length == 2 && msg[0].equals(command());
    }
}
