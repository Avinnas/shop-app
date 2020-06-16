package com.example.shopapp.service;

import com.example.shopapp.model.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CartService {
    UserRepository userRepository;
    CartRepository cartRepository;
    ItemRepository itemRepository;
    ItemService itemService;

    public CartService(UserRepository userRepository, CartRepository cartRepository, ItemRepository itemRepository, ItemService itemService) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.itemRepository = itemRepository;
        this.itemService = itemService;
    }

    public Cart createCart(int user_id){
        User user = userRepository.findById(user_id).orElseThrow();
        Cart cart = new Cart();
        user.setCart(cart);
        return cartRepository.save(cart);
    }

    public List<Cart> findAllCarts(){
        return cartRepository.findAll();
    }

    public void addToCart(int user_id, int item_id) {
        Item item = itemRepository.findById(item_id).orElseThrow();
        User user = userRepository.findById(user_id).orElseThrow();
        if (user.getCart() == null) {
            createCart(user_id);
        }
        item.setCart(user.getCart());

        itemRepository.save(item);
        userRepository.save(user);
    }

    public void buyAllItemsInCart(int user_id){
        Cart cart = userRepository.findById(user_id).orElseThrow().getCart();
        for (Item item : cart.getItems()){
            itemService.buyItem(user_id, item.getId());
            itemRepository.save(item);
        }
    }
    public List<Product> readProductsInCart(int user_id){
        User user = userRepository.findById(user_id).orElseThrow();
        if (user.getCart() == null) {
            createCart(user_id);
        }
        userRepository.save(user);
        Cart cart = user.getCart();
        return cart.getItems().stream()
                .map(Item::getProduct)
                .collect(Collectors.toList());
    }

    public void removeFromCart(int product_id, int user_id){
        int cart_id = userRepository.findById(user_id).orElseThrow().getCart().getId();
        Item item =itemRepository.findByProduct_IdAndCart_Id(product_id, cart_id).get(0);
        item.setCart(null);
        itemRepository.save(item);

    }
}
