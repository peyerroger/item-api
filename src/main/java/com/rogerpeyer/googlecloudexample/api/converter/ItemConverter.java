package com.rogerpeyer.googlecloudexample.api.converter;

import com.rogerpeyer.googlecloudexample.api.model.Item;
import com.rogerpeyer.googlecloudexample.persistence.model.ItemPo;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ItemConverter {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "version", ignore = true)
  ItemPo convert(Item item);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "version", ignore = true)
  ItemPo convert(@MappingTarget ItemPo current, Item item);

  Item convert(ItemPo itemPo);

  List<Item> convert(List<ItemPo> itemPos);
}
