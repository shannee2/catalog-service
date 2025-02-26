package com.catalog.controller;

import com.catalog.dto.item.MenuItemRequest;
import com.catalog.dto.restaurants.RestaurantRequest;
import com.catalog.dto.restaurants.RestaurantResponse;
import com.catalog.dto.user.UserRequest;
import com.catalog.dto.user.UserResponse;
import com.catalog.exceptions.users.UserNotFoundException;
import com.catalog.model.user.User;
import com.catalog.service.MenuItemService;
import com.catalog.service.RestaurantService;
import com.catalog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/restaurants")
public class MenuItemController {

    private final MenuItemService menuItemService;

    @Autowired
    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @PostMapping("/{restaurantId}/menu-item")
    public ResponseEntity<?> createItemInRestaurant(@PathVariable Long restaurantId,@RequestBody MenuItemRequest menuItemRequest) {
        Long Id = menuItemService.createMenuItem(restaurantId, menuItemRequest);
        Map<String, String> response = new HashMap();
        response.put("message", "Item has been created successfully");
        response.put("itemId", Id.toString());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
