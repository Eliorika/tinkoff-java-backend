package ru.tinkoff.edu.java.bot.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.client.ScrapperClient;

import java.util.Arrays;
import java.util.List;

@Component
public class CommandFactory {

    private final ScrapperClient scrapperClient;

    public CommandFactory(ScrapperClient scrapperClient) {
        this.scrapperClient = scrapperClient;
    }

    public List<Command> commands() {
        return Arrays.asList(
                new StartCommand(scrapperClient),
                new HelpCommand(scrapperClient),
                new TrackCommand(scrapperClient),
                new UntrackCommand(scrapperClient),
                new ListCommand(scrapperClient)
        );
    }
}
