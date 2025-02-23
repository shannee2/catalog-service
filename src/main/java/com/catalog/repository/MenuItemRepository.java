package com.catalog.repository;

import com.catalog.model.item.MenuItem;
import com.catalog.model.item.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    // Find all menu items for a specific restaurant
    List<MenuItem> findByRestaurantId(Long restaurantId);

    // Find menu items by category
    List<MenuItem> findByCategory(ItemCategory category);
}
