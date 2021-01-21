package com.rogerpeyer.itemsapi.api;

import com.rogerpeyer.itemsapi.api.converter.ItemConverter;
import com.rogerpeyer.itemsapi.api.model.Item;
import com.rogerpeyer.itemsapi.persistence.ItemRepository;
import com.rogerpeyer.itemsapi.persistence.model.ItemPo;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/items")
@AllArgsConstructor
@Transactional
class ItemController {

  private final ItemConverter itemConverter;
  private final ItemRepository itemRepository;

  @GetMapping
  public List<Item> findAll() {
    List<ItemPo> itemPos = itemRepository.findAll();
    return itemConverter.convert(itemPos);
  }

  @GetMapping(value = "/{id}")
  public Item findById(@PathVariable("id") Long id) {
    ItemPo itemPo =
        itemRepository.findById(id).orElseThrow(() -> new RuntimeException("Could not find Item."));
    return itemConverter.convert(itemPo);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Item create(@RequestBody Item item) {
    ItemPo itemPo = itemConverter.convert(item);
    itemPo = itemRepository.saveAndFlush(itemPo);
    return itemConverter.convert(itemPo);
  }

  @PutMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Item update(@PathVariable("id") Long id, @RequestBody Item item) {
    ItemPo itemPo =
        itemRepository.findById(id).orElseThrow(() -> new RuntimeException("Could not find Item."));
    itemPo = itemConverter.convert(itemPo, item);
    itemPo = itemRepository.saveAndFlush(itemPo);
    return itemConverter.convert(itemPo);
  }

  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void delete(@PathVariable("id") Long id) {
    itemRepository.deleteById(id);
  }
}
