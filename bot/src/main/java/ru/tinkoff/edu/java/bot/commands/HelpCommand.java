package ru.tinkoff.edu.java.bot.commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.tinkoff.edu.java.bot.UpdateProcessor;

public class HelpCommand implements Command{
    @Override
    public String command() {
        return "/help";
    }

    @Override
    public String about() {
        return "prints all commans";
    }

    @Override
    public SendMessage handleCommand(Update update) {
        SendMessage sm = new SendMessage();
        sm.setChatId(update.getMessage().getChatId());
        StringBuilder answer = new StringBuilder();
        answer.append("Commands I understand:\n");
        for (Command c : UpdateProcessor.commands()) {
            answer.append(c.command());
            answer.append(" - ");
            answer.append(c.about());
            answer.append("\n");
        }
        sm.setText(answer.toString());
        return sm;
    }
}
