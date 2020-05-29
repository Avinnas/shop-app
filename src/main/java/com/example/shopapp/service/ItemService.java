package com.example.shopapp.service;

import com.example.shopapp.dto.ProductItemDto;
import com.example.shopapp.model.Item;
import com.example.shopapp.model.ItemRepository;
import com.example.shopapp.model.Product;
import com.example.shopapp.model.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

//    ItemRepository itemRepository;
//    ProductRepository productRepository;
//
//    public ItemService(ItemRepository itemRepository, ProductRepository productRepository) {
//        this.itemRepository = itemRepository;
//        this.productRepository = productRepository;
//    }

    public ProductItemDto readItemDetails(Item item){
        return new ProductItemDto(item.getProduct(), item);
    }


}
