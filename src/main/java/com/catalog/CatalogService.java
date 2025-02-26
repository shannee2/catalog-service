package com.catalog;

import com.catalog.model.restaurant.Restaurant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CatalogService {
	public static void main(String[] args) {
		Restaurant res = new Restaurant();
		SpringApplication.run(CatalogService.class, args);
	}
}