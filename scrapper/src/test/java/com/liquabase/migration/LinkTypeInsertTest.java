package com.liquabase.migration;

import io.swagger.v3.oas.models.security.SecurityScheme;
import org.junit.Test;
import org.testcontainers.containers.JdbcDatabaseContainer;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class LinkTypeInsertTest extends  IntegrationEnvironment{

    @Test
    public void insertTest(){
        JdbcDatabaseContainer<?> c = IntegrationEnvironment.CONTAINER;
        IntegrationEnvironment.runMigrations(c);
        try {
            Statement st = DriverManager.getConnection(c.getJdbcUrl(), c.getUsername(), c.getPassword()).createStatement();
            int res = st.executeUpdate("INSERT INTO link_type(link_type) VALUES (\'github\')");
            assertEquals(res, 1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
