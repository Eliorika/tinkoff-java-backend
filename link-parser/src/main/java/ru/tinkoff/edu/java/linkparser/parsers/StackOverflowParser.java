package ru.tinkoff.edu.java.linkparser.parsers;

import ru.tinkoff.edu.java.linkparser.linkStructures.Result;
import ru.tinkoff.edu.java.linkparser.linkStructures.StackOverflowResult;
import ru.tinkoff.edu.java.linkparser.parsers.LinkParser;

import java.net.URL;
import java.util.Arrays;

public final class StackOverflowParser implements LinkParser {
    @Override
    public Result parseLink(URL url) {
        StackOverflowResult outResult = null;
        String[] aUrlsplit = url.getPath().split("/");
        int id = Arrays.asList(aUrlsplit).indexOf("questions");
        if(aUrlsplit.length > id && id != -1)
            outResult = new StackOverflowResult(Integer.parseInt(aUrlsplit[id+1]));
        return outResult;
    }
}
