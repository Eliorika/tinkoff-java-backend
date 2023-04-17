package com.liquabase.migration;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.DirectoryResourceAccessor;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.PlatformTransactionManager;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Testcontainers
public abstract class IntegrationEnvironment {
    protected static final JdbcDatabaseContainer<?> CONTAINER;

    static {
        CONTAINER = new PostgreSQLContainer<>("postgres:15.2-alpine")
                .withDatabaseName("scrapper")
                .withUsername("admin")
                .withPassword("0000");
        CONTAINER.start();

        runMigrations(CONTAINER);

    }

    @Configuration
    public static class EnvironmentConfig {

        @Bean
        public DataSource testDataSource() {
            return DataSourceBuilder.create()
                    .url(CONTAINER.getJdbcUrl())
                    .username(CONTAINER.getUsername())
                    .password(CONTAINER.getPassword())
                    .build();
        }
        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public PlatformTransactionManager transactionManager(DataSource dataSource) {
            return new JdbcTransactionManager(dataSource);
        }
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
