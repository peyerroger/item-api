package com.rogerpeyer.itemsapi.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.rogerpeyer.itemsapi.api.converter.ItemConverter;
import com.rogerpeyer.itemsapi.api.model.Item;
import com.rogerpeyer.itemsapi.persistence.ItemRepository;
import com.rogerpeyer.itemsapi.persistence.model.ItemPo;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ItemControllerTest {

  private static final Random random = new Random(42);

  @Mock private ItemRepository itemRepository;
  @Mock private ItemConverter itemConverter;

  @InjectMocks private ItemController itemController;

  @Test
  void findAll() {
    List<ItemPo> itemPos = new ArrayList<>();
    List<Item> items = new ArrayList<>();

    Mockito.when(itemRepository.findAll()).thenReturn(itemPos);
    Mockito.when(itemConverter.convert(itemPos)).thenReturn(items);

    List<Item> target = itemController.findAll();
    assertNotNull(target);


  }

  @Test
  void findById() {
    Long id = random.nextLong();
    ItemPo itemPo = new ItemPo();
    Item item = new Item();
    Mockito.when(itemRepository.findById(id)).thenReturn(Optional.of(itemPo));
    Mockito.when(itemConverter.convert(itemPo)).thenReturn(item);
    Item target = itemController.findById(id);
    assertEquals(item, target);
  }

  @Test
  void create() {
    ItemPo itemPo = new ItemPo();
    Item item = new Item();
    Mockito.when(itemConverter.convert(item)).thenReturn(itemPo);
    Mockito.when(itemRepository.saveAndFlush(itemPo)).thenReturn(itemPo);
    Mockito.when(itemConverter.convert(itemPo)).thenReturn(item);
    Item target = itemController.create(item);
    assertEquals(item, target);
  }

  @Test
  void update() {
    Long id = random.nextLong();
    ItemPo itemPo = new ItemPo();
    Item item = new Item();

    Mockito.when(itemRepository.findById(id)).thenReturn(Optional.of(itemPo));
    Mockito.when(itemConverter.convert(itemPo, item)).thenReturn(itemPo);
    Mockito.when(itemRepository.saveAndFlush(itemPo)).thenReturn(itemPo);
    Mockito.when(itemConverter.convert(itemPo)).thenReturn(item);

    Item target = itemController.update(id, item);

    assertEquals(item, target);
  }

  @Test
  void delete() {
    Long id = random.nextLong();
    itemController.delete(id);
    Mockito.verify(itemRepository).deleteById(id);
  }
}
