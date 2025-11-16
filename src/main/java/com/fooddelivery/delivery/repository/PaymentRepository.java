package com.fooddelivery.delivery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fooddelivery.delivery.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, String> {
	List<Payment> findByOrderId(Long orderId);
    List<Payment> findByStatus(Payment.PaymentStatus status);
    List<Payment> findByOrderIdAndStatus(Long orderId, Payment.PaymentStatus status);
    
    // Kiểm tra xem order đã có payment với status cụ thể chưa
    boolean existsByOrderIdAndStatus(Long orderId, Payment.PaymentStatus status);
}
