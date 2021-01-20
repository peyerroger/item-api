package com.rogerpeyer.itemsapi;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.rogerpeyer.itemsapi.common.BaseTest;
import java.net.URI;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;


public class ActuatorTest extends BaseTest {

  @Test
  public void testLiveness() {

    URI livenessUri =
        UriComponentsBuilder.newInstance()
            .scheme("http")
            .host("localhost")
            .port(actuatorPort)
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
            .port(actuatorPort)
            .path("/actuator/health/readiness")
            .build()
            .toUri();

    ResponseEntity<String> responseEntity = restTemplate.getForEntity(livenessUri, String.class);
    assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
  }
}
