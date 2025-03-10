package com.catalog.service;

import com.catalog.dto.item.MenuItemRequest;
import com.catalog.dto.item.MenuItemResponse;
import com.catalog.model.item.ItemCategory;
import com.catalog.model.item.MenuItem;
import com.catalog.model.money.Currency;
import com.catalog.model.money.CurrencyType;
import com.catalog.model.money.Money;
import com.catalog.model.restaurant.Restaurant;
import com.catalog.repository.MenuItemRepository;
import com.catalog.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MenuItemServiceTest {

    @Mock
    private MenuItemRepository menuItemRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private CurrencyService currencyService;

    @InjectMocks
    private MenuItemService menuItemService;

    private Restaurant restaurant;
    private Currency currency;
    private Money price;
    private MenuItem menuItem;
    private MenuItemRequest menuItemRequest;

    @BeforeEach
    void setUp() {
        restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setName("Pizza Place");

        currency = new Currency(CurrencyType.USD, 1);

        price = new Money(9.99, currency);

        menuItem = new MenuItem();
        menuItem.setId(1L);
        menuItem.setName("Margherita Pizza");
        menuItem.setDescription("Classic pizza with tomato and mozzarella");
        menuItem.setPrice(price);
        menuItem.setCategory(ItemCategory.MAIN_COURSE);
        menuItem.setRestaurant(restaurant);

        menuItemRequest = new MenuItemRequest(
                "Margherita Pizza",
                "Classic pizza with tomato and mozzarella",
                9.99,
                ItemCategory.MAIN_COURSE,
                "USD"
        );
    }

    @Test
    void testCreateMenuItem() {
        // Ensure menuItemRequest is correctly initialized
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurant));
        when(currencyService.getCurrency(eq("USD"))).thenReturn(currency); // Fix here
        when(menuItemRepository.save(any(MenuItem.class))).thenReturn(menuItem);

        Long menuItemId = menuItemService.createMenuItem(1L, menuItemRequest);

        assertNotNull(menuItemId);
        assertEquals(1L, menuItemId);
        verify(restaurantRepository, times(1)).findById(1L);
        verify(currencyService, times(1)).getCurrency("USD"); // Ensure correct argument
        verify(menuItemRepository, times(1)).save(any(MenuItem.class));
    }



    @Test
    void testConvertToMenuItemResponse() {
        MenuItemResponse response = menuItemService.convertToMenuItemResponse(menuItem);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Margherita Pizza", response.getName());
        assertEquals("Classic pizza with tomato and mozzarella", response.getDescription());
        assertEquals(9.99, response.getAmount());
        assertEquals("USD", response.getCurrency());
        assertEquals("MAIN_COURSE", response.getCategory());
    }

    @Test
    void testConvertToMenuItemResponseList() {
        List<MenuItemResponse> responses = menuItemService.convertToMenuItemResponse(List.of(menuItem));

        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals("Margherita Pizza", responses.get(0).getName());
    }
}
