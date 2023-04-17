package com.liquabase.migration;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.DirectoryResourceAccessor;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.DriverManager;
import java.sql.SQLException;

@Testcontainers
public abstract class IntegrationEnvironment {
    static final JdbcDatabaseContainer<?> CONTAINER;

    static {
        CONTAINER = new PostgreSQLContainer<>("postgres:15.2-alpine")
                .withDatabaseName("scrapper")
                .withUsername("admin")
                .withPassword("0000");
        CONTAINER.start();
    }

    @DynamicPropertySource
    static void jdbcProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", CONTAINER::getUsername);
        registry.add("spring.datasource.password", CONTAINER::getPassword);
    }

    public static void runMigrations(JdbcDatabaseContainer<?> c){
        var cangelogpath = new File(".").toPath().toAbsolutePath().getParent().getParent().resolve("migration").resolve("migrations");

        try {
            var conn = DriverManager.getConnection(c.getJdbcUrl(), c.getUsername(), c.getPassword());
            var changelogDir = new DirectoryResourceAccessor(cangelogpath);

            var db = new PostgresDatabase();
            db.setConnection(new JdbcConnection(conn));

            var liquibase = new Liquibase("master.yaml", changelogDir, db);
            liquibase.update(new Contexts(), new LabelExpression());

        } catch (FileNotFoundException | SQLException | LiquibaseException e){
            throw new RuntimeException();
        }

    }

}
