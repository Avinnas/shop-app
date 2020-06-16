package com.example.shopapp.controller;

import com.example.shopapp.service.CartService;
import com.example.shopapp.service.ProductService;
import com.example.shopapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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
    @ResponseBody
    public ModelAndView readAllProducts(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("cart");
        List list=readCart().getBody();
        modelAndView.addObject("incartprod", list);
        return modelAndView;
    }
    public ResponseEntity<List> readCart(){
        return ResponseEntity.ok(cartService.readProductsInCart(userService.getCurrentUserId()));
    }


    @GetMapping("/cart/{id}")
    ResponseEntity<?> addToCart(@PathVariable int id){
        productService.addProductToCart(userService.getCurrentUserId(), id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buy")
    ResponseEntity<?> buyAllItems(){
        cartService.buyAllItemsInCart(userService.getCurrentUserId());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/cart/{id}")
    ResponseEntity<?> deleteItemFromCart(@PathVariable int id){
        cartService.removeFromCart(id, userService.getCurrentUserId());
        return ResponseEntity.ok().build();
    }
}
