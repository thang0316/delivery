package com.fooddelivery.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fooddelivery.delivery.entity.Drone;

public interface DroneRepository extends JpaRepository<Drone, String> {
}
