package com.fooddelivery.delivery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // view login.html
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "admin-dashboard"; // view admin-dashboard.html
    }

    @GetMapping("/restaurant/dashboard")
    public String restaurantDashboard() {
        return "restaurant-dashboard"; // view restaurant-dashboard.html
    }

    @GetMapping("/customer/dashboard")
    public String customerDashboard() {
        return "customer-dashboard"; // view customer-dashboard.html
    }
}
