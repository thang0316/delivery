package com.fooddelivery.delivery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.fooddelivery.delivery.dto.request.RestaurantCreationRequest;
import com.fooddelivery.delivery.entity.Restaurant;
import com.fooddelivery.delivery.service.RestaurantService;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    // Tạo nhà hàng
    @PostMapping
    public Restaurant create(@RequestBody RestaurantCreationRequest request) {
        return restaurantService.createRestaurant(request);
    }

    // Lấy tất cả nhà hàng
    @GetMapping
    public List<Restaurant> getAll() {
        return restaurantService.getAllRestaurant();
    }

    // Lấy nhà hàng theo ID
    @GetMapping("/{id}")
    public Restaurant getById(@PathVariable String id) {
        return restaurantService.getRestaurantById(id).orElse(null);
    }

    // Cập nhật nhà hàng
    @PutMapping("/{id}")
    public Restaurant update(@PathVariable String id, @RequestBody RestaurantCreationRequest request) {
        return restaurantService.updateRestaurant(id, request);
    }

    // Xóa nhà hàng
    @DeleteMapping("/{id}")
    public void deleteRestaurant(@PathVariable String id) {
        restaurantService.deleteRestaurant(id);
    }
}
