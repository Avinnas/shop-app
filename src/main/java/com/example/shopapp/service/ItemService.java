package com.example.shopapp.service;

import com.example.shopapp.dto.ProductItemDto;
import com.example.shopapp.model.Item;
import com.example.shopapp.model.ItemRepository;
import com.example.shopapp.model.Product;
import com.example.shopapp.model.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
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

}
