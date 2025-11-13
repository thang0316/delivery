package com.fooddelivery.delivery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping("/customer")
public class CustomerPageController {

    // Dashboard chính
    @GetMapping("/dashboard")
    public String dashboard() {
        return "customer/dashboard";  // => templates/customer/dashboard.html
    }

    // Load page con: /customer/page?name=menu
    @GetMapping(value = "/page", produces = "text/html; charset=UTF-8")
    @ResponseBody
    public String loadPage(@RequestParam String name) throws IOException {

        // ⛔ Chặn truy cập file ngoài whitelist (bảo mật)
        if (!name.matches("^[a-zA-Z0-9_-]+$")) {
            return "<h3 style='color:red'>Invalid page name!</h3>";
        }

        // Đúng đường dẫn trong classpath
        String filePath = "templates/customer/" + name + ".html";

        ClassPathResource resource = new ClassPathResource(filePath);

        // Nếu file không tồn tại → trả lỗi
        if (!resource.exists()) {
            return "<h3 style='color:red'>❌ Page not found: " + name + "</h3>";
        }

        // Trả nội dung HTML
        return StreamUtils.copyToString(
                resource.getInputStream(),
                StandardCharsets.UTF_8
        );
    }
}
