package com.fooddelivery.delivery.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fooddelivery.delivery.dto.request.MenuItemRequest;
import com.fooddelivery.delivery.entity.MenuItem;
import com.fooddelivery.delivery.service.MenuItemService;

@RestController
@RequestMapping("/api/menu-items")
@CrossOrigin(origins = "*") // ✅ Cho phép gọi API từ frontend (localhost, file HTML, v.v.)
public class MenuItemController {

    @Autowired
    private MenuItemService menuItemService;


    // 🟢 Tạo món ăn mới (dành cho nhà hàng)
    @PostMapping
    public ResponseEntity<MenuItem> createMenuItem(@RequestBody MenuItemRequest request) {
        MenuItem created = menuItemService.createMenuItem(request);
        return ResponseEntity.ok(created);
    }


    // 🟢 Lấy tất cả món ăn (dành cho admin / người dùng)
    @GetMapping
    public ResponseEntity<List<MenuItem>> getAllMenuItems() {
        List<MenuItem> list = menuItemService.getAllMenuItems();
        return ResponseEntity.ok(list);
    }


    // 🟢 Lấy món ăn theo ID
    @GetMapping("/{id}")
    public ResponseEntity<MenuItem> getMenuItemById(@PathVariable Long id) {
        MenuItem item = menuItemService.getMenuItemById(id);
        return ResponseEntity.ok(item);
    }


    // 🟢 Lấy danh sách món ăn theo ID nhà hàng
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<MenuItem>> getMenuItemsByRestaurant(@PathVariable String restaurantId) {
        List<MenuItem> list = menuItemService.getMenuItemsByRestaurant(restaurantId);
        return ResponseEntity.ok(list);
    }


    // 🟡 Cập nhật món ăn (dành cho nhà hàng)
    @PutMapping("/{id}")
    public ResponseEntity<MenuItem> updateMenuItem(@PathVariable Long id, @RequestBody MenuItemRequest request) {
        MenuItem updated = menuItemService.updateMenuItem(id, request);
        return ResponseEntity.ok(updated);
    }


    // 🔴 Xóa món ăn
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMenuItem(@PathVariable Long id) {
        menuItemService.deleteMenuItem(id);
        return ResponseEntity.ok("🗑️ Đã xóa món ăn với ID: " + id);
    }
}
