package com.example.shopapp.service;

import com.example.shopapp.model.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
//    ItemRepository itemRepository;
//
//    public UserService(ItemRepository itemRepository) {
//        this.itemRepository = itemRepository;
//    }
//
//    public List<Item> readBoughtItems(User customer){
///        return itemRepository.findAllByCustomer_Id(customer.getId());
 //   }

    public User findUserByEmail(String email) ;
    public User saveUser(User user);
}
