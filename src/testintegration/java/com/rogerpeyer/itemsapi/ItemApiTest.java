package com.rogerpeyer.itemsapi;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import com.rogerpeyer.itemsapi.api.model.Item;
import java.net.URI;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = Application.class)
public class ItemApiTest {

  @LocalServerPort private int port;

  @Autowired private TestRestTemplate restTemplate;

  @Test
  public void test() {
    URI itemsUri =
        UriComponentsBuilder.newInstance()
            .scheme("http")
            .host("localhost")
            .port(port)
            .path("/items")
            .build()
            .toUri();

    // Post Item 1
    Item item1 = new Item();
    item1.setName(UUID.randomUUID().toString());

    ResponseEntity<Item> itemResponseEntity =
        restTemplate.postForEntity(itemsUri, item1, Item.class);
    assertThat(itemResponseEntity.getStatusCode(), is(HttpStatus.CREATED));

    item1 = itemResponseEntity.getBody();
    assertThat(item1, notNullValue());

    // Post Item 2
    Item item2 = new Item();
    item2.setName(UUID.randomUUID().toString());

    ResponseEntity<Item> itemResponseEntity2 =
        restTemplate.postForEntity(itemsUri, item2, Item.class);
    assertThat(itemResponseEntity2.getStatusCode(), is(HttpStatus.CREATED));

    item2 = itemResponseEntity2.getBody();
    assertThat(item2, notNullValue());

    // Get Single Item
    URI itemUri =
        UriComponentsBuilder.newInstance()
            .scheme("http")
            .host("localhost")
            .port(port)
            .path("/items/{id}")
            .buildAndExpand(item1.getId())
            .toUri();
    ResponseEntity<Item> itemResponseEntity3 = restTemplate.getForEntity(itemUri, Item.class);
    assertThat(itemResponseEntity3.getStatusCode(), is(HttpStatus.OK));

    Item getItem = itemResponseEntity3.getBody();
    assertThat(getItem, notNullValue());
    assertThat(getItem, is(item1));

    // Get All Items
    ResponseEntity<Item[]> itemResponseEntity4 = restTemplate.getForEntity(itemsUri, Item[].class);
    assertThat(itemResponseEntity4.getStatusCode(), is(HttpStatus.OK));

    Item[] getItems = itemResponseEntity4.getBody();
    assertThat(getItems, notNullValue());
    assertThat(2, is(getItems.length));

    // Put Item
    Item putItem = new Item();
    putItem.setName(UUID.randomUUID().toString());

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<Item> entity = new HttpEntity<>(putItem, headers);
    ResponseEntity<Item> itemResponseEntity5 =
        restTemplate.exchange(itemUri, HttpMethod.PUT, entity, Item.class);
    assertThat(itemResponseEntity5.getStatusCode(), is(HttpStatus.OK));

    Item returnedPutItem = itemResponseEntity5.getBody();
    assertThat(returnedPutItem, notNullValue());
    assertThat(returnedPutItem.getName(), is(putItem.getName()));
  }
}
