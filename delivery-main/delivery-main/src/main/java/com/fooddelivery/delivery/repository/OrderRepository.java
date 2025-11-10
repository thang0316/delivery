package com.fooddelivery.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.fooddelivery.delivery.entity.Order;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomer_Id(String customerId);
    List<Order> findByRestaurant_Id(String restaurantId);
}
