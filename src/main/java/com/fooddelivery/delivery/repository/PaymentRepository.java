package com.fooddelivery.delivery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fooddelivery.delivery.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, String> {
	List<Payment> findByOrderId(Long orderId);
    List<Payment> findByStatus(Payment.PaymentStatus status);
}
