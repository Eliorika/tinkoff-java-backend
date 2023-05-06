package ru.tinkoff.edu.java.scrapper.domain.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.domain.etities.ChatEntity;

@Repository
public interface JpaChatRepository extends JpaRepository<ChatEntity, Long> {

}
