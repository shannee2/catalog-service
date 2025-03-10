package com.catalog.service;

import com.catalog.dto.restaurants.RestaurantRequest;
import com.catalog.dto.restaurants.RestaurantResponse;
import com.catalog.model.item.MenuItem;
import com.catalog.model.restaurant.Restaurant;
import com.catalog.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private MenuItemService menuItemService;

    @InjectMocks
    private RestaurantService restaurantService;

    private Restaurant restaurant;
    private RestaurantRequest restaurantRequest;

    @BeforeEach
    void setUp() {
        restaurantRequest = new RestaurantRequest(
                "Pizza Place",
                "New York",
                "123 Main St",
                40.7128,
                -74.0060,
                "+1234567890"
        );

        restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setName(restaurantRequest.getName());
        restaurant.setCity(restaurantRequest.getCity());
        restaurant.setLocation(restaurantRequest.getLocation());
        restaurant.setLatitude(restaurantRequest.getLatitude());
        restaurant.setLongitude(restaurantRequest.getLongitude());
        restaurant.setContactNumber(restaurantRequest.getContactNumber());
    }

    @Test
    void testCreateRestaurant() {
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(restaurant);

        Long restaurantId = restaurantService.createRestaurant(restaurantRequest);

        assertNotNull(restaurantId);
        assertEquals(1L, restaurantId);
        verify(restaurantRepository, times(1)).save(any(Restaurant.class));
    }

    @Test
    void testGetAllRestaurants() {
        when(restaurantRepository.findAll()).thenReturn(List.of(restaurant));
        when(menuItemService.convertToMenuItemResponse(anyList())).thenReturn(List.of());

        List<RestaurantResponse> responses = restaurantService.getAllRestaurants();

        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals("Pizza Place", responses.get(0).getName());
        verify(restaurantRepository, times(1)).findAll();
    }

    @Test
    void testGetRestaurantById() {
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurant));
        when(menuItemService.convertToMenuItemResponse(anyList())).thenReturn(List.of());

        RestaurantResponse response = restaurantService.getRestaurant(1L);

        assertNotNull(response);
        assertEquals("Pizza Place", response.getName());
        verify(restaurantRepository, times(1)).findById(1L);
    }

    @Test
    void testGetRestaurantByCity() {
        when(restaurantRepository.findByCity(anyString())).thenReturn(List.of(restaurant));
        when(menuItemService.convertToMenuItemResponse(anyList())).thenReturn(List.of());

        List<RestaurantResponse> responses = restaurantService.getRestaurantByCity("New York");

        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals("Pizza Place", responses.get(0).getName());
        verify(restaurantRepository, times(1)).findByCity("New York");
    }
}
