package com.fooddelivery.delivery.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fooddelivery.delivery.dto.request.PaymentRequest;
import com.fooddelivery.delivery.entity.Order;
import com.fooddelivery.delivery.entity.Payment;
import com.fooddelivery.delivery.repository.OrderRepository;
import com.fooddelivery.delivery.repository.PaymentRepository;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    // 🔹 Tạo mới payment
    public Payment createPayment(PaymentRequest request) {
    	Order order = orderRepository.findById(request.getOrderId())
    	        .orElseThrow(() -> new RuntimeException("Order not found"));

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setMethod(request.getMethod());
        payment.setAmount(request.getAmount());
        payment.setStatus(request.getStatus());
        payment.setCreatedAt(LocalDateTime.now());

        return paymentRepository.save(payment);
    }

    // 🔹 Lấy tất cả payment
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    // 🔹 Lấy payment theo ID
    public Payment getPaymentById(String id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy payment với ID: " + id));
    }

    // 🔹 Lấy payment theo đơn hàng
    public List<Payment> getPaymentsByOrder(Long orderId) {
        return paymentRepository.findByOrderId(orderId);
    }

    // 🔹 Cập nhật payment
    public Payment updatePayment(String id, PaymentRequest request) {
        Payment payment = getPaymentById(id);

        if (request.getStatus() != null) payment.setStatus(request.getStatus());
        if (request.getAmount() != null) payment.setAmount(request.getAmount());
        if (request.getMethod() != null) payment.setMethod(request.getMethod());

        return paymentRepository.save(payment);
    }

    // 🔹 Xóa payment
    public void deletePayment(String id) {
        paymentRepository.deleteById(id);
    }
}
