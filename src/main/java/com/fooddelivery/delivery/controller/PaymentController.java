package com.fooddelivery.delivery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.fooddelivery.delivery.dto.request.PaymentRequest;
import com.fooddelivery.delivery.entity.Payment;
import com.fooddelivery.delivery.service.PaymentService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public Payment createPayment(@RequestBody PaymentRequest request) {
        return paymentService.createPayment(request);
    }

    @GetMapping
    public List<Payment> getAllPayments() {
        return paymentService.getAllPayments();
    }

    @GetMapping("/{id}")
    public Payment getPaymentById(@PathVariable String id) {
        return paymentService.getPaymentById(id);
    }

    @PutMapping("/{id}/status")
    public Payment updatePaymentStatus(@PathVariable String id, @RequestParam Payment.PaymentStatus status) {
        return paymentService.updatePayment(id, status);
    }

    @DeleteMapping("/{id}")
    public void deletePayment(@PathVariable String id) {
        paymentService.deletePayment(id);
    }
}
