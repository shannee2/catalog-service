package com.catalog.dto.restaurants;

import com.catalog.dto.item.MenuItemResponse;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantResponse {
    private Long id;
    private String name;
    private String city;
    private String location;
    private Double latitude;
    private Double longitude;
    private String contactNumber;
    private List<MenuItemResponse> menuItems;
}
