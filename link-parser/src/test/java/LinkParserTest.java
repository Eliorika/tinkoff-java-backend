import org.junit.jupiter.api.Test;
import ru.tinkoff.edu.java.linkparser.linkStructures.GitHubResult;
import ru.tinkoff.edu.java.linkparser.linkStructures.Result;
import ru.tinkoff.edu.java.linkparser.linkStructures.StackOverflowResult;
import ru.tinkoff.edu.java.linkparser.parsers.LinkParser;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LinkParserTest {

    @Test
    void parseGithubLink(){
        Result expected = new GitHubResult("sanyarnd", "tinkoff-java-course-2022");
        URL link;
        try {
            link = new URL("https://github.com/sanyarnd/tinkoff-java-course-2022/");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        assertEquals(expected, LinkParser.getLinkInfo(link));
    }

    @Test
    void parseValidStackOverflowLink(){
        Result expected = new StackOverflowResult(1642028);
        URL link;
        try {
            link = new URL("https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        assertEquals(expected, LinkParser.getLinkInfo(link));
    }

    @Test
    void parseInvalidLink(){
        Result expected = null;
        URL link;
        try {
            link = new URL("https://justtest/haveagooday/hopeitworks");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        assertEquals(expected, LinkParser.getLinkInfo(link));
    }

    @Test
    void parseInvalidStackOverflowLink(){
        Result expected = null;
        URL link;
        try {
            link = new URL("https://stackoverflow.com/search?q=unsupported%20link");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        assertEquals(expected, LinkParser.getLinkInfo(link));
    }
}
