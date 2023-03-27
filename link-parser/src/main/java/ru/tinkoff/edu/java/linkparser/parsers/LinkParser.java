package ru.tinkoff.edu.java.linkparser.parsers;

import ru.tinkoff.edu.java.linkparser.linkStructures.Result;

import java.net.URL;

public sealed interface LinkParser permits GitHubParser, StackOverflowParser, WrongHostParser {
    default Result parseLink(URL url) {
        return null;
    }

    static LinkParser defineHost(URL url){
        String sHost = url.getHost();
        LinkParser parser = new WrongHostParser();
        if(sHost.contains("github"))
            parser = new GitHubParser();
        else if (sHost.contains("stackoverflow"))
            parser = new StackOverflowParser();
        return parser;
    }
}
