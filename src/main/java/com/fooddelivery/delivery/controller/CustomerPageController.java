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

    @GetMapping("/dashboard")
    public String dashboard() {
        return "customer/dashboard";
    }

    @GetMapping(value = "/page", produces = "text/html; charset=UTF-8")
    @ResponseBody
    public String loadPage(@RequestParam String name) throws IOException {

        if (!name.matches("^[a-zA-Z0-9_-]+$"))
            return "<h3 style='color:red'>Invalid page name!</h3>";

        String filePath = "templates/customer/" + name + ".html";
        ClassPathResource resource = new ClassPathResource(filePath);

        if (!resource.exists())
            return "<h3 style='color:red'>Page not found: " + name + "</h3>";

        return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
    }
}
