package com.christianj98.pomodoro.integration;

import org.junit.jupiter.api.AfterAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;

@SpringBootTest
public abstract class IntegrationTestCleanup {

    @AfterAll
    public static void tearDown(@Autowired JdbcTemplate jdbcTemplate) throws IOException {
        final var resource = new ClassPathResource("db/changelog/integration/cleanup.sql");
        final var sql = new String(Files.readAllBytes(resource.getFile().toPath()));
        jdbcTemplate.execute(sql);
    }
}