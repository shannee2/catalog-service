package com.catalog.controller;

import com.catalog.dto.restaurants.RestaurantRequest;
import com.catalog.dto.restaurants.RestaurantResponse;
import com.catalog.dto.user.UserRequest;
import com.catalog.dto.user.UserResponse;
import com.catalog.exceptions.users.UserNotFoundException;
import com.catalog.model.user.User;
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
public class RestaurantController {

    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping
    public ResponseEntity<?> createRestaurant(@RequestBody RestaurantRequest restaurantRequest) {
        Long Id = restaurantService.createRestaurant(restaurantRequest);
        Map response = new HashMap();
        response.put("message", "Restaurant created successfully");
        response.put("restaurantId", Id);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<?> getAllRestaurants() {
        List<RestaurantResponse> restaurants = restaurantService.getAllRestaurants();
        return ResponseEntity.status(HttpStatus.OK).body(restaurants);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRestaurant(@PathVariable Long id) {
        RestaurantResponse restaurant = restaurantService.getRestaurant(id);
        return ResponseEntity.status(HttpStatus.OK).body(restaurant);
    }

    @GetMapping("/cities/{city}")
    public ResponseEntity<?> getRestaurant(@PathVariable String city) {
        List<RestaurantResponse> restaurants = restaurantService.getRestaurantByCity(city);
        return ResponseEntity.status(HttpStatus.OK).body(restaurants);
    }
}
