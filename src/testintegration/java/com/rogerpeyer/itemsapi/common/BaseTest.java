package com.rogerpeyer.itemsapi.common;

import com.rogerpeyer.itemsapi.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.web.server.LocalManagementPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = Application.class)
@Testcontainers
public abstract class BaseTest {

  @Container
  private static final PostgreSQLContainer<?> postgresqlContainer =
      new PostgreSQLContainer<>("postgres:13.1")
          .withDatabaseName("foo")
          .withUsername("foo")
          .withPassword("secret");

  @DynamicPropertySource
  static void registerPgProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgresqlContainer::getJdbcUrl);
    registry.add("spring.datasource.username", postgresqlContainer::getUsername);
    registry.add("spring.datasource.password", postgresqlContainer::getPassword);
  }

  @LocalServerPort protected int port;
  @LocalManagementPort protected int actuatorPort;

  @Autowired protected TestRestTemplate restTemplate;

}
