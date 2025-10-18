package com.fooddelivery.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fooddelivery.delivery.entity.Order;

public interface OrderRepository extends JpaRepository<Order, String> {
}
