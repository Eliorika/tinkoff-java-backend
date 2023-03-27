package ru.tinkoff.edu.java.linkparser.parsers;

import ru.tinkoff.edu.java.linkparser.linkStructures.GitHubResult;
import ru.tinkoff.edu.java.linkparser.linkStructures.Result;

import java.net.URL;

public final class GitHubParser implements LinkParser {
    @Override
    public Result parseLink(URL url) {
        String[] aUrlsplit = url.getPath().split("/");
        return new GitHubResult(aUrlsplit[1], aUrlsplit[2]);
    }
}
