package com.example.shopapp.service;

import com.example.shopapp.dto.ProductItemDto;
import com.example.shopapp.model.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class ItemService {

    ItemRepository itemRepository;
    UserRepository userRepository;

    public ItemService(ItemRepository itemRepository, UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    public ProductItemDto readItemDetails(Item item){
        return new ProductItemDto(item.getProduct(), item);
    }

    public ProductItemDto readItemDetails(int id){
        Item item = itemRepository.findById(id).orElseThrow();
        return new ProductItemDto(item.getProduct(), item);
    }
    public void saveItem(Item toSave){
        itemRepository.save(toSave);
    }

    public void buyItem(int user_id, int item_id){
        Item item = itemRepository.findById(item_id).orElseThrow();
        item.setSold(true);
        item.setCustomer(userRepository.findById(user_id).orElseThrow());
        Date in = new Date();
        item.setDateSold(LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault()));
        itemRepository.save(item);
    }

}
