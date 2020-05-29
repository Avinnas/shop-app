package com.example.shopapp.service;

import com.example.shopapp.model.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    ItemRepository itemRepository;

    public UserService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> readBoughtItems(User customer){
        return itemRepository.findAllByCustomer_Id(customer.getId());
    }
}
