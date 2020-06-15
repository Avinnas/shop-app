package com.example.shopapp.controller;

import com.example.shopapp.service.CartService;
import com.example.shopapp.service.ProductService;
import com.example.shopapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class CartController {
    CartService cartService;
    UserService userService;
    ProductService productService;

    public CartController(CartService cartService, UserService userService, ProductService productService) {
        this.cartService = cartService;
        this.userService = userService;
        this.productService = productService;
    }

    @GetMapping("/cart")
    public ResponseEntity<?> readCart(){
        return ResponseEntity.ok(cartService.readProductsInCart(userService.getCurrentUserId()));
    }

    @PutMapping("/cart/{id}")
    ResponseEntity<?> addToCart(@PathVariable int id){
        productService.addProductToCart(userService.getCurrentUserId(), id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/buy")
    ResponseEntity<?> buyAllItems(){
        cartService.buyAllItemsInCart(userService.getCurrentUserId());
        return ResponseEntity.noContent().build();
    }
}
