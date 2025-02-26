


package com.catalog.service;

import com.catalog.dto.item.MenuItemRequest;
import com.catalog.dto.item.MenuItemResponse;
import com.catalog.model.item.MenuItem;
import com.catalog.model.money.Currency;
import com.catalog.model.money.Money;
import com.catalog.model.restaurant.Restaurant;
import com.catalog.repository.MenuItemRepository;
import com.catalog.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final CurrencyService currencyService;

    public MenuItemService(MenuItemRepository menuItemRepository, RestaurantRepository restaurantRepository, CurrencyService currencyService) {
        this.menuItemRepository = menuItemRepository;
        this.restaurantRepository = restaurantRepository;
        this.currencyService = currencyService;
    }

    public Long createMenuItem(Long restaurantId, MenuItemRequest request) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));

        Currency currency = currencyService.getCurrency(request.getCurrencyType());

        Money price = new Money(request.getPrice(), currency);

        MenuItem menuItem = new MenuItem();
        menuItem.setName(request.getName());
        menuItem.setDescription(request.getDescription());
        menuItem.setPrice(price);
        menuItem.setCategory(request.getCategory());
        menuItem.setRestaurant(restaurant);

        return menuItemRepository.save(menuItem).getId();
    }

    MenuItemResponse convertToMenuItemResponse(MenuItem menuItem) {
        return MenuItemResponse.builder()
                .id(menuItem.getId())
                .name(menuItem.getName())
                .description(menuItem.getDescription())
                .amount(menuItem.getPrice().getAmount())
                .currency(menuItem.getPrice().getCurrency().getType().name())
                .category(menuItem.getCategory().name())
                .build();
    }

    List<MenuItemResponse> convertToMenuItemResponse(List<MenuItem> menuItems) {
        return menuItems.stream()
                .map(this::convertToMenuItemResponse)
                .collect(Collectors.toList());
    }

}
