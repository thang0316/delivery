package com.fooddelivery.delivery.controller;

import com.fooddelivery.delivery.dto.request.LoginRequest;
import com.fooddelivery.delivery.entity.User;
import com.fooddelivery.delivery.service.LoginService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class LoginController {

	@Autowired
    private LoginService loginService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/api/auth/login")
    @ResponseBody
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        User user = loginService.login(request);

        if (user == null) {
            return ResponseEntity.status(401).body("Sai tài khoản hoặc mật khẩu!");
        }

        return ResponseEntity.ok(user);
    }
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

}
