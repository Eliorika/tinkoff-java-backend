package ru.tinkoff.edu.java.scrapper.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.dto.request.LinkUpdateRequest;
import ru.tinkoff.edu.java.linkparser.linkStructures.GitHubResult;
import ru.tinkoff.edu.java.linkparser.linkStructures.StackOverflowResult;
import ru.tinkoff.edu.java.linkparser.parsers.LinkParser;
import ru.tinkoff.edu.java.scrapper.client.BotClient;
import ru.tinkoff.edu.java.scrapper.client.GitHubClient;
import ru.tinkoff.edu.java.scrapper.client.StackOverflowClient;
import ru.tinkoff.edu.java.scrapper.domain.dto.Link;
import ru.tinkoff.edu.java.scrapper.dto.response.GitHubResponse;
import ru.tinkoff.edu.java.scrapper.dto.response.StackOverflowResponse;
import ru.tinkoff.edu.java.scrapper.service.LinkUpdater;
import ru.tinkoff.edu.java.linkparser.linkStructures.Result;

import java.util.stream.Collectors;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class LinkUpdaterScheduler {
    private final LinkUpdater linkUpdater;
    private final GitHubClient gitHubClient;
    private final BotClient botClient;
    private final StackOverflowClient stackOverflowClient;

    @Scheduled(fixedDelayString = "${app.scheduler.interval}")
    public void update() {
        var updates = linkUpdater.findUpdates(linkUpdater.findOld());
        for(var row: updates.entrySet()){
            Link link = row.getKey();
            Result parseResult = LinkParser.getLinkInfo(link.getLink());
            if (parseResult instanceof GitHubResult ghResult){
                GitHubResponse response = gitHubClient.fetchRepository(ghResult.sUser(), ghResult.sRepository());
                if (response.getUpdatedAt().compareTo(link.getLastUpdated()) > -1) {
                    botClient.update(new LinkUpdateRequest(link.getLink_id(), link.getLink().toString(),
                            "New pushes in repo!", updates.get(link)));
                }
            } else if (parseResult instanceof StackOverflowResult soResult) {
                StackOverflowResponse response = stackOverflowClient.fetchQuestion(soResult.id());

                if (response.getUpdatedAt().compareTo(link.getLastUpdated()) > -1) {
                    botClient.update(new LinkUpdateRequest(link.getLink_id(), link.getLink().toString(),
                            "New updates in question!", updates.get(link)));
                }

                var question = response.getAnswersTime();
                int qAfterUpdate = question.stream().filter(time -> time.compareTo(link.getLastUpdated()) > -1).collect(Collectors.toList()).size();

                var comment = response.getCommentsTime();
                int cAfterUpdate = comment.stream().filter(time -> time.compareTo(link.getLastUpdated()) > -1).collect(Collectors.toList()).size();

                if(qAfterUpdate > 0){
                    botClient.update(new LinkUpdateRequest(link.getLink_id(), link.getLink().toString(),
                            "New answer(s)!", updates.get(link)));
                }

                if(cAfterUpdate > 0){
                    botClient.update(new LinkUpdateRequest(link.getLink_id(), link.getLink().toString(),
                            "New comment(s)!", updates.get(link)));
                }
            }
        }
        linkUpdater.update(linkUpdater.findOld());
    }
}
