package com.fooddelivery.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fooddelivery.delivery.entity.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, String> {
    
}
