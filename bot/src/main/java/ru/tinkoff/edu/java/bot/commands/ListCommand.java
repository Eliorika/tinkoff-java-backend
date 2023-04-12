package ru.tinkoff.edu.java.bot.commands;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.tinkoff.edu.java.bot.client.ScrapperClient;
import ru.tinkoff.edu.java.bot.client.dto.response.LinkResponse;
import ru.tinkoff.edu.java.bot.client.dto.response.ListLinksResponse;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ListCommand implements Command{
    private final ScrapperClient scrapperClient;
    @Override
    public String command() {
        return "/list";
    }

    @Override
    public String about() {
        return "get all links you are tracking";
    }

    @Override
    public SendMessage handleCommand(Update update) {
        long chatId = update.getMessage().getChatId();
        ListLinksResponse response = scrapperClient.getLinks(chatId);
        List<String> links = new ArrayList<>();
        if (response.size() == 0) {
            return new SendMessage(Long.toString(chatId), "There are no tracking links");
        }
        for (LinkResponse r : response.links()) {
            links.add(r.url().toString());
        }

        StringBuilder builder = new StringBuilder();
        builder.append("List of you tracking links\n");
        for (String link : links) {
            builder.append(link).append("\n");
        }
        return new SendMessage(Long.toString(chatId), builder.toString());
    }
}
