package com.fooddelivery.delivery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fooddelivery.delivery.entity.Drone;
import com.fooddelivery.delivery.entity.Restaurant;

public interface DroneRepository extends JpaRepository<Drone, String> {
	List<Drone> findByStatus(String status);
    List<Drone> findByRestaurant(Restaurant restaurant);
    
}
