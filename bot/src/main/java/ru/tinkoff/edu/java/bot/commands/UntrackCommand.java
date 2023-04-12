package ru.tinkoff.edu.java.bot.commands;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.tinkoff.edu.java.bot.client.ScrapperClient;
import ru.tinkoff.edu.java.bot.client.dto.request.AddLinkRequest;
import ru.tinkoff.edu.java.bot.client.dto.request.RemoveLinkRequest;

@RequiredArgsConstructor
public class UntrackCommand implements Command{

    private final ScrapperClient scrapperClient;
    @Override
    public String command() {
        return "/untrack";
    }

    @Override
    public String about() {
        return "remove link from a tracking list. Format: \"/untrack [link]\"";
    }

    @Override
    public SendMessage handleCommand(Update update) {
        long chatId = update.getMessage().getChatId();
        String message = update.getMessage().getText();
        String link = message.substring(command().length()).trim();
        scrapperClient.removeLink(chatId, new RemoveLinkRequest(link));
        return new SendMessage(Long.toString(chatId), "Stop tracking " + link);
    }

    @Override
    public boolean supports(Update update) {
        String message = update.getMessage().getText();
        String[] msg = message.split(" ");
        return msg.length == 2 && msg[0].equals(command());
    }
}
