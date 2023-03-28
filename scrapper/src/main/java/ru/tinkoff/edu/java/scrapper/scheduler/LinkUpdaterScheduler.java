package ru.tinkoff.edu.java.scrapper.scheduler;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class LinkUpdaterScheduler {
    @Scheduled(fixedDelayString = "${app.scheduler.interval}")
    public void update() {
    }
}
