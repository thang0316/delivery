package com.fooddelivery.delivery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fooddelivery.delivery.dto.request.RestaurantCreationRequest;
import com.fooddelivery.delivery.entity.Restaurant;
import com.fooddelivery.delivery.service.RestaurantService;

@RestController
@RequestMapping("/api/restaurants")
@CrossOrigin(origins = "*") // ✅ Cho phép frontend (dashboard.html) gọi API từ localhost
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    // ===================== TẠO NHÀ HÀNG =====================
    @PostMapping
    public Restaurant createRestaurant(@RequestBody RestaurantCreationRequest request) {
        return restaurantService.createRestaurant(request);
    }

    // ===================== LẤY TẤT CẢ NHÀ HÀNG =====================
    @GetMapping
    public List<Restaurant> getAllRestaurants() {
        return restaurantService.getAllRestaurant();
    }

    // ===================== LẤY THEO ID =====================
    @GetMapping("/{id}")
    public Restaurant getRestaurantById(@PathVariable String id) {
        return restaurantService.getRestaurantById(id).orElse(null);
    }

    // ===================== CẬP NHẬT =====================
    @PutMapping("/{id}")
    public Restaurant updateRestaurant(
            @PathVariable String id,
            @RequestBody RestaurantCreationRequest request) {
        return restaurantService.updateRestaurant(id, request);
    }

    // ===================== CẬP NHẬT TRẠNG THÁI HOẠT ĐỘNG =====================
    @PutMapping("/{id}/active")
    public Restaurant updateRestaurantStatus(
            @PathVariable String id,
            @RequestParam Boolean active) {
        return restaurantService.updateRestaurantStatus(id, active);
    }

    // ===================== XÓA =====================
    @DeleteMapping("/{id}")
    public void deleteRestaurant(@PathVariable String id) {
        restaurantService.deleteRestaurant(id);
    }
}
