package com.catalog.model.restaurant;

import com.catalog.model.item.MenuItem;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "restaurants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    private String contactNumber;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MenuItem> menuItems = new ArrayList<>();

    public Restaurant(Long id, String name, String city, String location, Double latitude, Double longitude, String contactNumber) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.contactNumber = contactNumber;
    }

    public void addMenuItem(MenuItem menuItem) {
        menuItems.add(menuItem);
        menuItem.setRestaurant(this);
    }
}
