package com.rogerpeyer.itemsapi;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.rogerpeyer.itemsapi.api.model.Item;
import com.rogerpeyer.itemsapi.common.BaseTest;
import java.net.URI;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

public class WelcomeApiTest extends BaseTest {

  @Test
  public void test() {
    URI welcomeUri =
        UriComponentsBuilder.newInstance()
            .scheme("http")
            .host("localhost")
            .port(port)
            .path("/")
            .build()
            .toUri();

    ResponseEntity<String> welcomeResponse =
        restTemplate.getForEntity(welcomeUri, String.class);
    assertThat(welcomeResponse.getStatusCode(), is(HttpStatus.OK));
    assertThat(welcomeResponse.getBody(), is("Welcome"));
}
}
