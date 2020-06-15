package com.example.shopapp.controller;

import com.example.shopapp.model.ItemRepository;
import com.example.shopapp.model.Product;
import com.example.shopapp.model.ProductRepository;
import com.example.shopapp.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
//    List<Product> readAllProducts() {
//        return productService.findAllProducts();
//    }
    public ModelAndView readAllProducts(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("products");
        List list=repository.findAll();
        modelAndView.addObject("products", list);
        return modelAndView;
    }

    @PostMapping("/products")
    Product createProduct(@RequestBody Product toCreate){
        return repository.save(toCreate);

    }
    @PostMapping("/products/{id}/{quantity}")
    ResponseEntity<?> addItems(@PathVariable int id, @PathVariable int quantity){
        productService.addProductItems(id, quantity);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/products/{name}/{quantity}")
    ResponseEntity<?> addProductsFromExternalShop(@PathVariable String name, @PathVariable int quantity){
        return ResponseEntity.ok(productService.getProductsFromShop(name, quantity));
    }

}
