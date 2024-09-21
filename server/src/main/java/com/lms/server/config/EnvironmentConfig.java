package com.lms.server.config;

import io.github.cdimascio.dotenv.Dotenv;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class EnvironmentConfig {

    private static final Logger logger = LoggerFactory.getLogger(EnvironmentConfig.class);

    @Bean
    public Dotenv dotenv() {
        return Dotenv.configure().ignoreIfMissing().load();
    }

    @Bean
    public String setEnvironmentVariables(Environment env, Dotenv dotenv) {
        dotenv.entries().forEach(entry -> {
            System.setProperty(entry.getKey(), entry.getValue());
            // logger.info("Loaded environment variable: {}={}", entry.getKey(),
            // entry.getValue());
        });
        logger.info("Environment variables set");
        return "Environment variables set";
    }

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:postgresql://" + dotenv().get("DB_HOST") + ":" + dotenv().get("DB_PORT") + "/"
                        + dotenv().get("DB_NAME"))
                .username(dotenv().get("DB_USER"))
                .password(dotenv().get("DB_PASSWORD"))
                .build();
    }
}