package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.time.Duration;

@Configuration
public class DataBaseConfig {
    @Value("${spring.datasource.url}")
    private static String url = "jdbc:postgresql://localhost:5432/scrapper";

    @Value("${spring.datasource.username}")
    private static String username = "arina";

    @Value("${spring.datasource.password}")
    private static String password = "0000";

    @Value("${spring.datasource.driver-class-name}")
    private static String driver = "org.postgresql.Driver";
    @Bean
    @Primary
    public DataSource dataSource() {
        System.out.println(url);
        return DataSourceBuilder
                .create()
                .driverClassName(driver)
                .url(url)
                .username(username)
                .password(password)
                .build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

}
