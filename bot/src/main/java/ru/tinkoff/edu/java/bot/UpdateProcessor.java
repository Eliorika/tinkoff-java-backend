package ru.tinkoff.edu.java.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.tinkoff.edu.java.bot.commands.*;

import java.util.Arrays;
import java.util.List;

public class UpdateProcessor {

    private UpdateProcessor(){}

    public static List<Command> commands() {
        return Arrays.asList(
                new StartCommand(),
                new HelpCommand(),
                new TrackCommand(),
                new UntrackCommand(),
                new ListCommand()
        );
    }

    public static SendMessage process(Update update){
        if (update.getMessage() == null) {
            return null;
        }
        String command = update.getMessage().getText().split(" ")[0];

        if (command.startsWith("/")) {
            for (Command c : commands()) {
                if (c.supports(update)) {
                    return c.handleCommand(update);
                }
            }
        }
        SendMessage sm = new SendMessage();
        sm.setChatId(update.getMessage().getChatId());
        sm.setText("Sorry, I have no idea what are you talking about. Use /help to see list of commands I know");
        return sm;
    }
}
