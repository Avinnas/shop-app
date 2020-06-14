package com.example.shopapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.shopapp.model.ExternalShopHandler;

@SpringBootApplication
public class ShopAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopAppApplication.class, args);

		// jedynie dla sprawdzenia czy działa
//		ExternalShopHandler externalShop = new ExternalShopHandler();
//		String phrase = "iphone X 64gb";
//		var products = externalShop.getItemsAsListFromExternalShop(phrase, 10);
//
//		System.out.println("---");
//		System.out.println(products.get(0).getPrice());
//		System.out.println(externalShop.getPriceOfProduct("iphone X 64gb"));
//		System.out.println(externalShop.getPriceOfProduct("Karta Podarunkowa prezent na slub - 100 zl"));
	}
}
