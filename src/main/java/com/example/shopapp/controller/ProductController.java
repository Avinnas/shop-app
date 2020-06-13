package com.example.shopapp.controller;

import com.example.shopapp.model.Product;
import com.example.shopapp.model.ProductRepository;
import com.example.shopapp.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private ProductRepository repository;
    private ProductService productService;

    public ProductController(ProductRepository repository, ProductService productService) {
        this.repository = repository;
        this.productService = productService;
    }

    @GetMapping("/products")
    List<Product> readAllProducts(){
        return productService.findAllProducts();
    }

    @PostMapping("/products")
    Product createProduct(@RequestBody Product toCreate){
        return repository.save(toCreate);

    }

}
