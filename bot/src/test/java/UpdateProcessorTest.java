import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.tinkoff.edu.java.bot.UpdateProcessor;
import ru.tinkoff.edu.java.bot.client.ClientConfiguration;
import ru.tinkoff.edu.java.bot.client.ScrapperClient;
import ru.tinkoff.edu.java.bot.commands.CommandFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UpdateProcessorTest {
    @Test
    public void testProcessUnknownCommand() {
        ClientConfiguration cl = new ClientConfiguration();
        ScrapperClient scrapperClient = Mockito.mock(ScrapperClient.class);
        CommandFactory commandFactory = new CommandFactory(scrapperClient);
        UpdateProcessor updateProcessor = new UpdateProcessor(commandFactory);


        long chatId = 123L;
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(chatId);
        Update update = Mockito.mock(Update.class);
        Mockito.when(update.getMessage()). thenReturn(message);

        String command = "/something_just_went_wrong";
        Mockito.when(message.getText()).thenReturn(command);

        SendMessage result = updateProcessor.process(update);
        SendMessage expected = new SendMessage(Long.toString(chatId), "Sorry, I have no idea what are you talking about. Use /help to see list of commands I know");

        assertEquals(result.getChatId(), expected.getChatId());
        assertEquals(result.getText(), expected.getText());
    }
}
