package com.rogerpeyer.itemsapi.api.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.rogerpeyer.itemsapi.api.model.Item;
import com.rogerpeyer.itemsapi.persistence.model.ItemPo;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class ItemConverterTest {

  private static final Random random = new Random(42);

  private final ItemConverter itemConverter = Mappers.getMapper(ItemConverter.class);

  @Test
  void convertItem() {
    Item item = new Item();
    item.setId(random.nextLong());
    item.setVersion(random.nextInt());
    item.setName(UUID.randomUUID().toString());

    ItemPo itemPo = itemConverter.convert(item);

    assertNull(itemPo.getId());
    assertNull(itemPo.getVersion());
    assertEquals(item.getName(), itemPo.getName());
  }

  @Test
  void convertItem1() {

    Item item = new Item();
    item.setId(random.nextLong());
    item.setVersion(random.nextInt());
    item.setName(UUID.randomUUID().toString());

    ItemPo current = new ItemPo();
    current.setId(random.nextLong());
    current.setVersion(random.nextInt());
    current.setName(UUID.randomUUID().toString());

    ItemPo target = itemConverter.convert(current, item);

    assertEquals(current.getId(), target.getId());
    assertEquals(current.getVersion(), target.getVersion());
    assertEquals(item.getName(), target.getName());
  }

  @Test
  void convertItemPo() {
    ItemPo itemPo = new ItemPo();
    itemPo.setId(random.nextLong());
    itemPo.setVersion(random.nextInt());
    itemPo.setName(UUID.randomUUID().toString());

    Item item = itemConverter.convert(itemPo);

    assertEquals(itemPo.getId(), item.getId());
    assertEquals(itemPo.getVersion(), item.getVersion());
    assertEquals(itemPo.getName(), item.getName());
  }

  @Test
  void convertItem3() {
    List<Item> items = itemConverter.convert(Arrays.asList(new ItemPo(), new ItemPo()));
    assertNotNull(items);
    assertEquals(2, items.size());
  }
}
