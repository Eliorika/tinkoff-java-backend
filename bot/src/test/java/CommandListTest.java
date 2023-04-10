import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.tinkoff.edu.java.bot.UpdateProcessor;
import ru.tinkoff.edu.java.bot.client.ClientConfiguration;
import ru.tinkoff.edu.java.bot.client.ScrapperClient;
import ru.tinkoff.edu.java.bot.client.dto.response.LinkResponse;
import ru.tinkoff.edu.java.bot.client.dto.response.ListLinksResponse;
import ru.tinkoff.edu.java.bot.commands.CommandFactory;
import ru.tinkoff.edu.java.bot.commands.ListCommand;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandListTest {
    @Test
    public void testListHandleWithEmptyLinks() {
        ScrapperClient scrapperClient = Mockito.mock(ScrapperClient.class);
        ListCommand listCommand = new ListCommand(scrapperClient);
        long chatId = 123L;
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(chatId);
        Update update = Mockito.mock(Update.class);
        Mockito.when(update.getMessage()).thenReturn(message);
        Mockito.when(scrapperClient.getLinks(chatId)).thenReturn(new ListLinksResponse(Collections.emptyList(), 0));

        SendMessage result = listCommand.handleCommand(update);
        SendMessage expected = new SendMessage(Long.toString(chatId), "There are no tracking links");

        assertEquals(result.getChatId(), expected.getChatId());
        assertEquals(result.getText(), expected.getText());
    }

    @Test
    public void testListHandleWithLinks() throws URISyntaxException {
        ScrapperClient scrapperClient = Mockito.mock(ScrapperClient.class);
        ListCommand listCommand = new ListCommand(scrapperClient);
        long chatId = 123L;
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(chatId);
        Update update = Mockito.mock(Update.class);
        Mockito.when(update.getMessage()). thenReturn(message);
        List<LinkResponse> links = new ArrayList<>();
        links.add(new LinkResponse(1L, new URI("https://github.com/Eliorika/tinkoff-java-backend")));
        links.add(new LinkResponse(2L, new URI("https://stackoverflow.com/questions/4130857/why-companies-prefer-java-for-programming")));
        Mockito.when(scrapperClient.getLinks(chatId)).thenReturn(new ListLinksResponse(links, links.size()));

        SendMessage result = listCommand.handleCommand(update);
        SendMessage expected = new SendMessage(Long.toString(chatId), """
                List of you tracking links
                https://github.com/Eliorika/tinkoff-java-backend
                https://stackoverflow.com/questions/4130857/why-companies-prefer-java-for-programming
                """);

        assertEquals(result.getChatId(), expected.getChatId());
        assertEquals(result.getText(), expected.getText());
    }
}
