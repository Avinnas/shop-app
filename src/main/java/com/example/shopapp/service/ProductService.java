package com.example.shopapp.service;

import com.example.shopapp.model.Product;
import com.example.shopapp.model.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    ProductRepository productRepository;

    public List<Product> findAllProducts(){
        return productRepository.findAll();
    }

    public Product findProductById(int id){
        return productRepository.findById(id).orElseThrow();
    }
}
