package com.fooddelivery.delivery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Trang chính sau khi login thành công
    @GetMapping("/")
    public String home() {
        return "index"; // trỏ đến templates/index.html
    }


}
