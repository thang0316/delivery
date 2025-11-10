package com.fooddelivery.delivery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fooddelivery.delivery.entity.MenuItem;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
	List<MenuItem> findByRestaurantId(String restaurantId);
}
