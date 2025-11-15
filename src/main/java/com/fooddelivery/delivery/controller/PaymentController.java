package com.fooddelivery.delivery.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import com.fooddelivery.delivery.dto.request.PaymentRequest;
import com.fooddelivery.delivery.entity.Payment;
import com.fooddelivery.delivery.service.PaymentService;

import jakarta.servlet.http.HttpServletRequest;

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
    
    // ==================== VNPAY ENDPOINTS ====================
    
    /**
     * API tạo URL thanh toán VNPay
     * Frontend gọi API này, nhận URL và redirect người dùng đến trang VNPay
     * 
     * @param orderId ID của đơn hàng cần thanh toán
     * @param request HttpServletRequest để lấy IP address
     * @return URL redirect đến VNPay
     */
    @PostMapping("/vnpay/create")
    public String createVNPayPayment(@RequestParam Long orderId, HttpServletRequest request) 
            throws UnsupportedEncodingException {
        String ipAddress = request.getRemoteAddr();
        return paymentService.createVNPayPaymentUrl(orderId, ipAddress);
    }
    
    /**
     * API callback từ VNPay (người dùng sẽ được redirect về đây sau khi thanh toán)
     * URL này phải khớp với vnp-return-url trong application.yaml
     * 
     * @param request HttpServletRequest chứa tham số VNPay trả về
     * @return Redirect đến trang kết quả thanh toán của frontend
     */
    @GetMapping("/vnpay/return")
    public RedirectView handleVNPayReturn(HttpServletRequest request) {
        try {
            Payment payment = paymentService.handleVNPayCallback(request);
            
            // Redirect đến trang thành công hoặc thất bại của frontend
            if (payment.getStatus() == Payment.PaymentStatus.COMPLETED) {
                return new RedirectView("/payment/success?orderId=" + payment.getOrder().getId());
            } else {
                return new RedirectView("/payment/failed?orderId=" + payment.getOrder().getId());
            }
        } catch (Exception e) {
            return new RedirectView("/payment/error?message=" + e.getMessage());
        }
    }
}
