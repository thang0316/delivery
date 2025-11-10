package com.fooddelivery.delivery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.util.StreamUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping("/admin")
public class AdminPageController {

    // Trang dashboard chính
    @GetMapping("/dashboard")
    public String dashboard() {
        return "admin/dashboard"; // templates/admin/dashboard.html
    }

    // ✅ Các trang con được load qua AJAX
    @GetMapping(value = "/users.html", produces = "text/html; charset=UTF-8")
    @ResponseBody
    public String usersPartial() throws IOException {
        return StreamUtils.copyToString(
                new ClassPathResource("templates/admin/users.html").getInputStream(),
                StandardCharsets.UTF_8
        );
    }

    @GetMapping(value = "/orders.html", produces = "text/html; charset=UTF-8")
    @ResponseBody
    public String ordersPartial() throws IOException {
        return StreamUtils.copyToString(
                new ClassPathResource("templates/admin/orders.html").getInputStream(),
                StandardCharsets.UTF_8
        );
    }

    @GetMapping(value = "/restaurants.html", produces = "text/html; charset=UTF-8")
    @ResponseBody
    public String restaurantsPartial() throws IOException {
        return StreamUtils.copyToString(
                new ClassPathResource("templates/admin/restaurants.html").getInputStream(),
                StandardCharsets.UTF_8
        );
    }

    @GetMapping(value = "/payments.html", produces = "text/html; charset=UTF-8")
    @ResponseBody
    public String paymentsPartial() throws IOException {
        return StreamUtils.copyToString(
                new ClassPathResource("templates/admin/payments.html").getInputStream(),
                StandardCharsets.UTF_8
        );
    }

    @GetMapping(value = "/drones.html", produces = "text/html; charset=UTF-8")
    @ResponseBody
    public String dronesPartial() throws IOException {
        return StreamUtils.copyToString(
                new ClassPathResource("templates/admin/drones.html").getInputStream(),
                StandardCharsets.UTF_8
        );
    }

    // ✅ Thêm mapping mới cho trang GIAO HÀNG
    @GetMapping(value = "/deliveries.html", produces = "text/html; charset=UTF-8")
    @ResponseBody
    public String deliveriesPartial() throws IOException {
        return StreamUtils.copyToString(
                new ClassPathResource("templates/admin/deliveries.html").getInputStream(),
                StandardCharsets.UTF_8
        );
    }
}
