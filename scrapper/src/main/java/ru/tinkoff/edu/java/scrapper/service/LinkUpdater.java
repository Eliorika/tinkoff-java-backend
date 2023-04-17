package ru.tinkoff.edu.java.scrapper.service;

import ru.tinkoff.edu.java.scrapper.domain.dto.Link;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface LinkUpdater {
    int update(List<Link> links);

    List<Link> findOld();

    Map<Link, List<Long>> findUpdates(List<Link> links);
}
