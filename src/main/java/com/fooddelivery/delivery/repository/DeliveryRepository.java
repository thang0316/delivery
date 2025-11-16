package com.fooddelivery.delivery.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fooddelivery.delivery.entity.Delivery;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, String> {
    Optional<Delivery> findByOrder_Id(Long orderId);
}

