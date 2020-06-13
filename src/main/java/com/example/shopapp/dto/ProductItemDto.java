package com.example.shopapp.dto;

import com.example.shopapp.model.Item;
import com.example.shopapp.model.Product;
import com.sun.istack.NotNull;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class ProductItemDto {

    @NotBlank(message = "Name must be not blank")
    private String name;
    @NotNull
    private float price;

    private String description;

    private LocalDateTime dateSold;

    public ProductItemDto(Product product, Item item){
        this.name = product.getName();
        this.price = product.getPrice();
        this.description = product.getDescription();
        this.dateSold = item.getDateSold();
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDateSold() {
        return dateSold;
    }
}
