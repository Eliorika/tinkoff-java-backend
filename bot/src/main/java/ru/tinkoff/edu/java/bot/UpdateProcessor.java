package ru.tinkoff.edu.java.bot;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.tinkoff.edu.java.bot.commands.*;

import java.util.List;


@Component
public class UpdateProcessor {

    @Getter
    private static List<Command> commands;

    public UpdateProcessor(CommandFactory factory) {
        commands = factory.commands();
    }


    public SendMessage process(Update update){
        if (update.getMessage() == null) {
            return null;
        }
        String command = update.getMessage().getText().split(" ")[0];

        if (command.startsWith("/")) {
            for (Command c : commands) {
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
