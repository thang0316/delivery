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
@RequestMapping("/restaurant")
public class RestaurantPageController {

    // Dashboard chính cho nhà hàng
    @GetMapping("/dashboard")
    public String dashboard() {
        return "restaurant/dashboard"; // templates/restaurant/dashboard.html
    }

    // Các trang con được load qua AJAX hoặc fetch
    @GetMapping(value = "/menu.html", produces = "text/html; charset=UTF-8")
    @ResponseBody
    public String menuPage() throws IOException {
        return StreamUtils.copyToString(
                new ClassPathResource("templates/restaurant/menu.html").getInputStream(),
                StandardCharsets.UTF_8
        );
    }

    @GetMapping(value = "/orders.html", produces = "text/html; charset=UTF-8")
    @ResponseBody
    public String ordersPage() throws IOException {
        return StreamUtils.copyToString(
                new ClassPathResource("templates/restaurant/orders.html").getInputStream(),
                StandardCharsets.UTF_8
        );
    }

    @GetMapping(value = "/payments.html", produces = "text/html; charset=UTF-8")
    @ResponseBody
    public String paymentsPage() throws IOException {
        return StreamUtils.copyToString(
                new ClassPathResource("templates/restaurant/payments.html").getInputStream(),
                StandardCharsets.UTF_8
        );
    }

    @GetMapping(value = "/profile.html", produces = "text/html; charset=UTF-8")
    @ResponseBody
    public String profilePage() throws IOException {
        return StreamUtils.copyToString(
                new ClassPathResource("templates/restaurant/profile.html").getInputStream(),
                StandardCharsets.UTF_8
        );
    }
}
