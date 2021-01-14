package com.rogerpeyer.googlecloudexample.persistence;

import com.rogerpeyer.googlecloudexample.persistence.model.ItemPo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<ItemPo, Long> {

}
