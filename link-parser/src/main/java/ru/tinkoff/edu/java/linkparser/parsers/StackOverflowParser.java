package ru.tinkoff.edu.java.linkparser.parsers;

import ru.tinkoff.edu.java.linkparser.linkStructures.Result;
import ru.tinkoff.edu.java.linkparser.linkStructures.StackOverflowResult;
import ru.tinkoff.edu.java.linkparser.parsers.LinkParser;

import java.net.URI;
import java.net.URL;
import java.util.Arrays;

public final class StackOverflowParser implements LinkParser {
    @Override
    public Result parseLink(URI url) {
        String[] aUrlsplit = url.getPath().split("/");
        int id = Arrays.asList(aUrlsplit).indexOf("questions");
        if(aUrlsplit.length > id && id != -1)
            return new StackOverflowResult(Long.parseLong(aUrlsplit[id+1]));
        return null;
    }
}
