package com.fooddelivery.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.fooddelivery.delivery.entity.Order;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomer_Id(String customerId);
    List<Order> findByRestaurant_Id(String restaurantId);
    List<Order> findByStatus(Order.OrderStatus status);
    List<Order> findByRestaurant_IdAndStatus(String restaurantId, Order.OrderStatus status);
    List<Order> findByCustomer_IdAndStatus(String customerId, Order.OrderStatus status);
    List<Order> findByCustomer_IdOrderByCreatedAtDesc(String customerId);
    List<Order> findByRestaurant_IdOrderByCreatedAtDesc(String restaurantId);
    long countByRestaurant_Id(String restaurantId);

}
