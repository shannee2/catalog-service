package com.catalog.controller;

import com.catalog.dto.item.MenuItemRequest;
import com.catalog.model.item.ItemCategory;
import com.catalog.model.item.MenuItem;
import com.catalog.model.money.Currency;
import com.catalog.model.money.CurrencyType;
import com.catalog.model.money.Money;
import com.catalog.model.restaurant.Restaurant;
import com.catalog.repository.MenuItemRepository;
import com.catalog.service.MenuItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
public class MenuItemControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MenuItemService menuItemService;

    @Mock
    private MenuItemRepository menuItemRepository;

    @InjectMocks
    private MenuItemController menuItemController;

    private final Restaurant burgerKing = new Restaurant(
            1L, "Burger King", "Indore", "Indore",
            22.745031356811523, 75.8681996, "98989283422"
    );

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(menuItemController).build();
    }

    @Test
    public void testCreateItemInRestaurant() throws Exception {
        ItemCategory category = ItemCategory.MAIN_COURSE;

        MenuItemRequest menuItemRequest = new MenuItemRequest("Burger", "Delicious beef burger", 5.99, category, "USD");

        when(menuItemService.createMenuItem(anyLong(), any(MenuItemRequest.class))).thenReturn(1L);

        mockMvc.perform(post("/restaurants/1/menu-item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(menuItemRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Item has been created successfully"));
    }
}