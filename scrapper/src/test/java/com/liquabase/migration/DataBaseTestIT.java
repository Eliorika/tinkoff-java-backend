package com.liquabase.migration;

import org.junit.Test;
import org.testcontainers.containers.JdbcDatabaseContainer;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class DataBaseTestIT extends  IntegrationEnvironment{

    @Test
    public void insertTest(){
        JdbcDatabaseContainer<?> c = IntegrationEnvironment.CONTAINER;
        IntegrationEnvironment.runMigrations(c);
        try {
            Statement st = DriverManager.getConnection(c.getJdbcUrl(), c.getUsername(), c.getPassword()).createStatement();
            int res = st.executeUpdate("INSERT INTO links(link, last_checked) VALUES ('github', now())");
            assertEquals(res, 1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
