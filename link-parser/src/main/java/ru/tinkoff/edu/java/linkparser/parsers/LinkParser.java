package ru.tinkoff.edu.java.linkparser.parsers;

import ru.tinkoff.edu.java.linkparser.linkStructures.Result;

import java.net.URI;
import java.net.URL;

public sealed interface LinkParser permits GitHubParser, StackOverflowParser, WrongHostParser {
    default Result parseLink(URI url) {
        return null;
    }

    private static LinkParser defineHost(URI url){
        String sHost = url.getHost();
        LinkParser parser = new WrongHostParser();
        if(sHost.contains("github"))
            parser = new GitHubParser();
        else if (sHost.contains("stackoverflow"))
            parser = new StackOverflowParser();
        return parser;
    }

    static Result getLinkInfo(URI url){
        LinkParser lp = defineHost(url);
        return lp.parseLink(url);
    }
}
