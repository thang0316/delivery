package com.fooddelivery.delivery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

//    @GetMapping("/admin/dashboard")
//    public String adminDashboard() {
//        return "admin/dashboard"; // trỏ tới templates/admin/dashboard.html
//    }
    
    @GetMapping("/payment/vnpay-test")
    public String vnpayTestPage() {
        return "payment/vnpay-test"; // templates/payment/vnpay-test.html
    }
    
    @GetMapping("/payment/success")
    public String paymentSuccess() {
        return "payment/success"; // templates/payment/success.html
    }
    
    @GetMapping("/payment/failed")
    public String paymentFailed() {
        return "payment/failed"; // templates/payment/failed.html
    }
    
    @GetMapping("/payment/error")
    public String paymentError() {
        return "payment/error"; // templates/payment/error.html
    }
}
