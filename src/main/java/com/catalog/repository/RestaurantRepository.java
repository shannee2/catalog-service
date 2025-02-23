package com.catalog.repository;

import com.catalog.model.restaurant.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    // Custom Query: Find restaurants by city
    List<Restaurant> findByCity(String city);

    // Custom Query: Find by name (optional if uniqueness is enforced)
    Restaurant findByName(String name);
}
