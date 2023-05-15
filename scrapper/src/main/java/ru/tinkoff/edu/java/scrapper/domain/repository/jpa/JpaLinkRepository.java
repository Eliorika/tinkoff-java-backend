package ru.tinkoff.edu.java.scrapper.domain.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.domain.etities.LinkEntity;

import java.util.List;

@Repository
public interface JpaLinkRepository extends JpaRepository<LinkEntity, Long> {

    List<LinkEntity> findAllByChats(long chatId);
    LinkEntity findByLink(String link);

    @Query(value = "select * from links where links.last_checked < now() - interval '1 minutes'", nativeQuery = true)
    List<LinkEntity> findOld();

}
