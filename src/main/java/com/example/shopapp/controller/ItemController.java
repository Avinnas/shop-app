package com.example.shopapp.controller;

import com.example.shopapp.model.Item;
import com.example.shopapp.model.ItemRepository;
import com.example.shopapp.service.ItemService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ItemController {
    ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/items/{id}")
    ResponseEntity<?> getItemData(@PathVariable int id){
        return ResponseEntity.ok(itemService.readItemDetails(id));
    }

    @PostMapping("/items")
    ResponseEntity<?> createItem (@RequestBody Item toCreate) {
        itemService.saveItem(toCreate);
        return ResponseEntity.noContent().build();
    }

}
