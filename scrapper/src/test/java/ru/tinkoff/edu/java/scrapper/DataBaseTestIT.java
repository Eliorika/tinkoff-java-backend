package ru.tinkoff.edu.java.scrapper;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.repository.IntegrationEnvironment;
import org.junit.Test;
import org.testcontainers.containers.JdbcDatabaseContainer;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(classes = {IntegrationEnvironment.EnvironmentConfig.class})
public class DataBaseTestIT extends IntegrationEnvironment {

    @Test
    public void existTest(){
        JdbcDatabaseContainer<?> c = IntegrationEnvironment.CONTAINER;
        IntegrationEnvironment.runMigrations(c);
        try {
            Statement st = DriverManager.getConnection(c.getJdbcUrl(), c.getUsername(), c.getPassword()).createStatement();
            var res = st.execute("select * from links");
            assertEquals(res, true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
