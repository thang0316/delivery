package com.fooddelivery.delivery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/restaurant")
public class RestaurantPageController {

    @GetMapping("/dashboard")
    public String dashboard() {
        return "restaurant/dashboard";
    }

    @GetMapping("/menu")
    public String menu() {
        return "restaurant/menu";
    }

    @GetMapping("/orders")
    public String orders() {
        return "restaurant/orders";
    }

    @GetMapping("/payments")
    public String payments() {
        return "restaurant/payments";
    }

    @GetMapping("/profile")
    public String profile() {
        return "restaurant/profile";
    }

    @GetMapping("/drones")
    public String drones() {
        return "restaurant/drones";  // ⭐ Gộp form + danh sách
    }
}
