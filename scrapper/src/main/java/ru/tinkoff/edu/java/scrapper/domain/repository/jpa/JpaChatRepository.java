package ru.tinkoff.edu.java.scrapper.domain.repository.jpa;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.domain.etities.ChatEntity;

import java.util.List;

@Repository
public interface JpaChatRepository extends JpaRepository<ChatEntity, Long> {

}
