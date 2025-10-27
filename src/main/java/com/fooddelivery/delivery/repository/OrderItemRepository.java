package com.fooddelivery.delivery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fooddelivery.delivery.entity.OrderItem;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
	// lấy danh sách các món ăn thuộc một đơn hàng cụ thể.
    List<OrderItem> findByOrder_Id(Long orderId);
}
