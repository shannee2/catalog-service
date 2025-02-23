package com.catalog.model.restaurant;

import com.catalog.dto.restaurants.RestaurantRequest;
import com.catalog.model.restaurant.Restaurant;
import com.catalog.repository.RestaurantRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
public class RestaurantTest {

    private final String RESTAURANT_BASE_URL = "/restaurants";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private RestaurantRepository restaurantRepository;

    private final Restaurant burgerKing = new Restaurant(
            1L, "Burger King", "Indore", "Indore",
            22.745031356811523, 75.8681996, "98989283422"
    );

    private final Restaurant dominos = new Restaurant(
            2L, "Dominos", "Indore", "Indore",
            22.745031356811523, 75.8681996, "98989283422"
    );

    private final Restaurant kfc = new Restaurant(
            3L, "KFC", "Pune", "Pune",
            22.745031356811523, 75.8681996, "98989283422"
    );

    @BeforeEach
    void setUp() {

    }

    @Test
    public void testCreateRestaurant() throws Exception {
        when(restaurantRepository.save(any(Restaurant.class)))
                .thenAnswer(invocation -> {
                    Restaurant restaurant = invocation.getArgument(0);
                    restaurant.setId(1L);
                    return restaurant;
                });

        RestaurantRequest request = new RestaurantRequest(
                "Burger King", "Indore", "Indore",
                22.745031356811523, 75.8681996, "98989283422"
        );

        mockMvc.perform(MockMvcRequestBuilders.post(RESTAURANT_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.restaurantId").isNumber());
    }

    @Test
    public void testGetAllRestaurants() throws Exception {
        when(restaurantRepository.findAll())
                .thenReturn(List.of(burgerKing, dominos, kfc));

        mockMvc.perform(MockMvcRequestBuilders.get(RESTAURANT_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(burgerKing.getId()))
                .andExpect(jsonPath("$[0].name").value(burgerKing.getName()))
                .andExpect(jsonPath("$[0].city").value(burgerKing.getCity()))
                .andExpect(jsonPath("$[0].location").value(burgerKing.getLocation()))
                .andExpect(jsonPath("$[0].latitude").value(burgerKing.getLatitude()))
                .andExpect(jsonPath("$[0].longitude").value(burgerKing.getLongitude()))
                .andExpect(jsonPath("$[0].contactNumber").value(burgerKing.getContactNumber()))
                .andExpect(jsonPath("$[1].id").value(dominos.getId()))
                .andExpect(jsonPath("$[1].name").value(dominos.getName()))
                .andExpect(jsonPath("$[1].city").value(dominos.getCity()))
                .andExpect(jsonPath("$[1].location").value(dominos.getLocation()))
                .andExpect(jsonPath("$[1].latitude").value(dominos.getLatitude()))
                .andExpect(jsonPath("$[1].longitude").value(dominos.getLongitude()))
                .andExpect(jsonPath("$[1].contactNumber").value(dominos.getContactNumber()))
                .andExpect(jsonPath("$[2].id").value(kfc.getId()))
                .andExpect(jsonPath("$[2].name").value(kfc.getName()))
                .andExpect(jsonPath("$[2].city").value(kfc.getCity()))
                .andExpect(jsonPath("$[2].location").value(kfc.getLocation()))
                .andExpect(jsonPath("$[2].latitude").value(kfc.getLatitude()))
                .andExpect(jsonPath("$[2].longitude").value(kfc.getLongitude()))
                .andExpect(jsonPath("$[2].contactNumber").value(kfc.getContactNumber()));
    }

    @Test
    public void testGetARestaurantById() throws Exception {
        when(restaurantRepository.findById(1L))
                .thenReturn(Optional.of(burgerKing));

        mockMvc.perform(MockMvcRequestBuilders.get(RESTAURANT_BASE_URL+"/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(burgerKing.getId()))
                .andExpect(jsonPath("$.name").value(burgerKing.getName()))
                .andExpect(jsonPath("$.city").value(burgerKing.getCity()))
                .andExpect(jsonPath("$.location").value(burgerKing.getLocation()))
                .andExpect(jsonPath("$.latitude").value(burgerKing.getLatitude()))
                .andExpect(jsonPath("$.longitude").value(burgerKing.getLongitude()))
                .andExpect(jsonPath("$.contactNumber").value(burgerKing.getContactNumber()));
    }

    @Test
    public void testGetARestaurantByCity() throws Exception {
        when(restaurantRepository.findByCity("Indore"))
                .thenReturn(List.of(burgerKing, dominos));

        mockMvc.perform(MockMvcRequestBuilders.get(RESTAURANT_BASE_URL+"/cities/Indore")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(burgerKing.getId()))
                .andExpect(jsonPath("$[0].name").value(burgerKing.getName()))
                .andExpect(jsonPath("$[0].city").value(burgerKing.getCity()))
                .andExpect(jsonPath("$[0].location").value(burgerKing.getLocation()))
                .andExpect(jsonPath("$[0].latitude").value(burgerKing.getLatitude()))
                .andExpect(jsonPath("$[0].longitude").value(burgerKing.getLongitude()))
                .andExpect(jsonPath("$[0].contactNumber").value(burgerKing.getContactNumber()))
                .andExpect(jsonPath("$[1].id").value(dominos.getId()))
                .andExpect(jsonPath("$[1].name").value(dominos.getName()))
                .andExpect(jsonPath("$[1].city").value(dominos.getCity()))
                .andExpect(jsonPath("$[1].location").value(dominos.getLocation()))
                .andExpect(jsonPath("$[1].latitude").value(dominos.getLatitude()))
                .andExpect(jsonPath("$[1].longitude").value(dominos.getLongitude()))
                .andExpect(jsonPath("$[1].contactNumber").value(dominos.getContactNumber()));
    }
}
