package com.example.shopapp.service;

import com.example.shopapp.model.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    ProductRepository productRepository;
    ItemRepository itemRepository;
    CartService cartService;

    public ProductService(ProductRepository productRepository, ItemRepository itemRepository, CartService cartService) {
        this.productRepository = productRepository;
        this.itemRepository = itemRepository;
        this.cartService = cartService;
    }

    public List<Product> findAllProducts(){
        return productRepository.findAll();
    }

    public Product findProductById(int id){
        return productRepository.findById(id).orElseThrow();
    }

    public void addProductToCart(int user_id, int product_id){
        int item_id = itemRepository.findByProduct_IdAndSoldIsFalseAndCartIsNull(product_id).get(0).getId();
        cartService.addToCart(user_id, item_id);
    }

    public void addProductItems(int product_id, int quantity){
        Product product = productRepository.findById(product_id).orElseThrow();
        for(int i=0; i<quantity; ++i){
            Item item = new Item();
            item.setProduct(product);
            itemRepository.save(item);
        }
    }

    public List<Product> getProductsFromShop(String phrase, int quantity){
        ExternalShopHandler handler = new ExternalShopHandler();
        ArrayList<Product> products = handler.getItemsAsListFromExternalShop(phrase, quantity);
        productRepository.saveAll(products);
        for (Product product : products){
            addProductItems(product.getId(), 10);
            product.setPrice(product.getPrice() * 1.2f);
        }
        return products;
    }
    public List<Product> getProductsFromShop(String phrase){
        return this.getProductsFromShop(phrase, 10);
    }

}
