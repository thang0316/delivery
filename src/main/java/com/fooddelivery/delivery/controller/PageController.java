package com.fooddelivery.delivery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/users-ui")
    public String usersPage() {
        return "users";
    }

    @GetMapping("/deliveries-ui")
    public String deliveriesPage() {
        return "deliveries";
    }
}
