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
@RequestMapping("/customer")
public class CustomerPageController {

    // Dashboard chính cho khách hàng
    @GetMapping("/dashboard")
    public String dashboard() {
        return "customer/dashboard"; // templates/customer/dashboard.html
    }

    @GetMapping(value = "/orders.html", produces = "text/html; charset=UTF-8")
    @ResponseBody
    public String ordersPage() throws IOException {
        return StreamUtils.copyToString(
                new ClassPathResource("templates/customer/orders.html").getInputStream(),
                StandardCharsets.UTF_8
        );
    }

    @GetMapping(value = "/payments.html", produces = "text/html; charset=UTF-8")
    @ResponseBody
    public String paymentsPage() throws IOException {
        return StreamUtils.copyToString(
                new ClassPathResource("templates/customer/payments.html").getInputStream(),
                StandardCharsets.UTF_8
        );
    }

    @GetMapping(value = "/feedback.html", produces = "text/html; charset=UTF-8")
    @ResponseBody
    public String feedbackPage() throws IOException {
        return StreamUtils.copyToString(
                new ClassPathResource("templates/customer/feedback.html").getInputStream(),
                StandardCharsets.UTF_8
        );
    }

    @GetMapping(value = "/profile.html", produces = "text/html; charset=UTF-8")
    @ResponseBody
    public String profilePage() throws IOException {
        return StreamUtils.copyToString(
                new ClassPathResource("templates/customer/profile.html").getInputStream(),
                StandardCharsets.UTF_8
        );
    }
}