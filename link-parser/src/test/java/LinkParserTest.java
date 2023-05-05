import org.junit.jupiter.api.Test;
import ru.tinkoff.edu.java.linkparser.linkStructures.GitHubResult;
import ru.tinkoff.edu.java.linkparser.linkStructures.Result;
import ru.tinkoff.edu.java.linkparser.linkStructures.StackOverflowResult;
import ru.tinkoff.edu.java.linkparser.parsers.LinkParser;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LinkParserTest {

    @Test
    void parseGithubLink(){
        Result expected = new GitHubResult("sanyarnd", "tinkoff-java-course-2022");
        URI link;
        try {
            link = new URI("https://github.com/sanyarnd/tinkoff-java-course-2022/");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        assertEquals(expected, LinkParser.getLinkInfo(link));
    }

    @Test
    void parseValidStackOverflowLink(){
        Result expected = new StackOverflowResult(1642028L);
        URI link;
        try {
            link = new URI("https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        assertEquals(expected, LinkParser.getLinkInfo(link));
    }

    @Test
    void parseInvalidLink(){
        Result expected = null;
        URI link;
        try {
            link = new URI("https://justtest/haveagooday/hopeitworks");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        assertEquals(expected, LinkParser.getLinkInfo(link));
    }

    @Test
    void parseInvalidStackOverflowLink(){
        Result expected = null;
        URI link;
        try {
            link = new URI("https://stackoverflow.com/search?q=unsupported%20link");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        assertEquals(expected, LinkParser.getLinkInfo(link));
    }
}
