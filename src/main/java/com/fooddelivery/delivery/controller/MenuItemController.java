package com.fooddelivery.delivery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.fooddelivery.delivery.dto.request.MenuItemRequest;
import com.fooddelivery.delivery.entity.MenuItem;
import com.fooddelivery.delivery.service.MenuItemService;

@RestController
@RequestMapping("/api/menu-items")
public class MenuItemController {

    @Autowired
    private MenuItemService menuItemService;

    // Tạo món ăn mới (nhà hàng)
    @PostMapping
    public MenuItem createMenuItem(@RequestBody MenuItemRequest request) {
        return menuItemService.createMenuItem(request);
    }

    // Lấy tất cả món ăn (dành cho admin hoặc người dùng xem)
    @GetMapping
    public List<MenuItem> getAllMenuItems() {
        return menuItemService.getAllMenuItems();
    }

    // Lấy món ăn theo ID
    @GetMapping("/{id}")
    public MenuItem getMenuItemById(@PathVariable Long id) {
        return menuItemService.getMenuItemById(id);
    }

    // Lấy danh sách món ăn của 1 nhà hàng
    @GetMapping("/restaurant/{restaurantId}")
    public List<MenuItem> getMenuItemsByRestaurant(@PathVariable String restaurantId) {
        return menuItemService.getMenuItemsByRestaurant(restaurantId);
    }

    //Cập nhật món ăn (nhà hàng)
    @PutMapping("/{id}")
    public MenuItem updateMenuItem(@PathVariable Long id, @RequestBody MenuItemRequest request) {
        return menuItemService.updateMenuItem(id, request);
    }

    // Xóa món ăn (nhà hàng)
    @DeleteMapping("/{id}")
    public String deleteMenuItem(@PathVariable Long id) {
        menuItemService.deleteMenuItem(id);
        return "Đã xóa món ăn với ID: " + id;
    }
}
