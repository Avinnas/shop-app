package com.example.shopapp.controller;

import com.example.shopapp.model.Product;
import com.example.shopapp.model.ProductRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private ProductRepository repository;

    public ProductController(ProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/products")
    List<Product> readAllProducts(){
        return repository.findAll();
    }

    @PostMapping("/products")
    Product createProduct(@RequestBody Product toCreate){
        return repository.save(toCreate);
    }

}
