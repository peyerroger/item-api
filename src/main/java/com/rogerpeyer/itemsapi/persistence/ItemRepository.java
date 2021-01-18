package com.rogerpeyer.itemsapi.persistence;

import com.rogerpeyer.itemsapi.persistence.model.ItemPo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<ItemPo, Long> {

}
