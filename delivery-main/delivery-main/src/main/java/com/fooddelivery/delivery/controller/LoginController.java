package com.fooddelivery.delivery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

//    @GetMapping("/")
//    public String index() {
//        return "index"; // templates/index.html
//    }

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // templates/login.html
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register"; // templates/register.html
    }
}
