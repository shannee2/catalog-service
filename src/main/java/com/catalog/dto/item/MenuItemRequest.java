package com.catalog.dto.item;

import com.catalog.model.item.ItemCategory;

import java.math.BigDecimal;

import com.catalog.model.money.CurrencyType;
import lombok.*;

@Getter
public class MenuItemRequest {
    private String name;
    private String description;
    private double price;
    private ItemCategory category;
    private String currencyType;

    public MenuItemRequest() {}

    public MenuItemRequest(String name, String description, double price, ItemCategory category, String currencyType) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.currencyType = currencyType;
    }


}
