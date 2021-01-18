package com.rogerpeyer.itemsapi;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.net.URI;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.web.server.LocalManagementPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = Application.class)
public class ActuatorTest {

  @LocalManagementPort private int port;

  @Autowired private TestRestTemplate restTemplate;

  @Test
  public void testLiveness() {

    URI livenessUri =
        UriComponentsBuilder.newInstance()
            .scheme("http")
            .host("localhost")
            .port(port)
            .path("/actuator/health/liveness")
            .build()
            .toUri();

    ResponseEntity<String> responseEntity = restTemplate.getForEntity(livenessUri, String.class);
    assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
  }

  @Test
  public void testReadiness() {

    URI livenessUri =
        UriComponentsBuilder.newInstance()
            .scheme("http")
            .host("localhost")
            .port(port)
            .path("/actuator/health/readiness")
            .build()
            .toUri();

    ResponseEntity<String> responseEntity = restTemplate.getForEntity(livenessUri, String.class);
    assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
  }
}
