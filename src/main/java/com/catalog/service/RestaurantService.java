package com.catalog.service;

import com.catalog.dto.restaurants.RestaurantRequest;
import com.catalog.dto.restaurants.RestaurantResponse;
//import com.catalog.exceptions.wallets.WalletNotFoundException;
import com.catalog.model.restaurant.Restaurant;
import com.catalog.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Primary
public class RestaurantService{

    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final AuthenticationManager authManager;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final MenuItemService menuItemService;


    @Autowired
    public RestaurantService(
            UserRepository userRepository, RestaurantRepository restaurantRepository,
            @Lazy AuthenticationManager authManager,
            PasswordEncoder encoder,
            JWTService jwtService, MenuItemService menuItemService) {
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.authManager = authManager;
        this.passwordEncoder = encoder;
        this.jwtService = jwtService;
        this.menuItemService = menuItemService;
    }

    public Long createRestaurant(RestaurantRequest restaurantRequest) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantRequest.getName());
        restaurant.setCity(restaurantRequest.getCity());
        restaurant.setLocation(restaurantRequest.getLocation());
        restaurant.setLatitude(restaurantRequest.getLatitude());
        restaurant.setLongitude(restaurantRequest.getLongitude());
        restaurant.setContactNumber(restaurantRequest.getContactNumber());
        return restaurantRepository.save(restaurant).getId();
    }

    public List<RestaurantResponse> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return convertToRestaurantResponse(restaurants);
    }

    public RestaurantResponse getRestaurant(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow();
        return convertToRestaurantResponse(restaurant);
    }

    public List<RestaurantResponse> getRestaurantByCity(String city) {
        List<Restaurant> restaurants = restaurantRepository.findByCity(city);
        return convertToRestaurantResponse(restaurants);
    }

    private RestaurantResponse convertToRestaurantResponse(Restaurant restaurant) {
        return new RestaurantResponse(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getCity(),
                restaurant.getLocation(),
                restaurant.getLatitude(),
                restaurant.getLongitude(),
                restaurant.getContactNumber(),
                menuItemService.convertToMenuItemResponse(restaurant.getMenuItems())
        );
    }

    private List<RestaurantResponse> convertToRestaurantResponse(List<Restaurant> restaurants) {
        return restaurants.stream()
                .map(this::convertToRestaurantResponse)
                .collect(Collectors.toList());
    }

}