package com.rogerpeyer.itemsapi.api;

import com.rogerpeyer.itemsapi.api.model.Item;
import com.rogerpeyer.itemsapi.persistence.model.ItemPo;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class WelcomeController {
  @GetMapping
  public String welcome() {
    return "Welcome";
  }
}
