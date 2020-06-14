package com.example.shopapp.controller;

import com.example.shopapp.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartController {
    CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/carts")
    public ResponseEntity<?> readAllCarts(){
        return ResponseEntity.ok(cartService.findAllCarts());
    }
}
