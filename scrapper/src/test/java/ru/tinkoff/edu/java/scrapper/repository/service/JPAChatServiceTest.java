package ru.tinkoff.edu.java.scrapper.repository.service;

import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import ru.tinkoff.edu.java.scrapper.ServicesConfiguration;
import ru.tinkoff.edu.java.scrapper.repository.IntegrationEnvironment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.tinkoff.edu.java.scrapper.domain.repository.jpa.JpaChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.repository.jpa.JpaLinkRepository;
import ru.tinkoff.edu.java.scrapper.service.jpa.JpaChatService;

import static org.junit.jupiter.api.Assertions.assertTrue;



@DataJpaTest(excludeAutoConfiguration = LiquibaseAutoConfiguration.class)
@Import({IntegrationEnvironment.JpaConfig.class, ServicesConfiguration.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@SpringBootTest(classes = {IntegrationEnvironment.EnvironmentConfig.class, ServicesConfiguration.class,
        //JpaChatRepository.class, JpaChatService.class, JpaLinkRepository.class})
//@SpringBootTest(classes = IntegrationEnvironment.EnvironmentConfig.class)
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
//@ContextConfiguration(classes = {ServicesConfiguration.class})
public class JPAChatServiceTest extends IntegrationEnvironment {

    @Autowired
    private JpaChatRepository jpaChatRepository;

    @Autowired
    private JpaLinkRepository jpaLinkRepository;


    @Autowired
    private JpaChatService jpaChatService;


    @Test
    public void registrationTest(){
        long id = 222;
        jpaChatService.register(id);

        assertTrue(jpaChatRepository.findById(id).orElse(null)!= null);
    }

    @Test
    public void unregistrationTest(){
        long id = 123;
        jpaChatService.register(id);
        jpaChatService.unregister(id);

        assertTrue(jpaChatRepository.findById(id).orElse(null)== null);
    }

}
