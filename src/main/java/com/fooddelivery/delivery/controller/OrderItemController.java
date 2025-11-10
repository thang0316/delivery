package com.fooddelivery.delivery.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fooddelivery.delivery.entity.OrderItem;
import com.fooddelivery.delivery.service.OrderItemService;

@RestController
@RequestMapping("/api/order-items")
@CrossOrigin(origins = "*")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;


    // 🟢 Thêm món ăn vào đơn hàng
    @PostMapping
    public ResponseEntity<OrderItem> addOrderItem(
            @RequestParam Long orderId,
            @RequestParam Long menuItemId,
            @RequestParam int quantity) {
        OrderItem added = orderItemService.addOrderItem(orderId, menuItemId, quantity);
        return ResponseEntity.ok(added);
    }


    // 🟢 Lấy danh sách món trong 1 đơn hàng
    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderItem>> getItemsByOrder(@PathVariable Long orderId) {
        List<OrderItem> items = orderItemService.getItemsByOrder(orderId);
        return ResponseEntity.ok(items);
    }


    // 🟢 Lấy chi tiết 1 món trong đơn hàng
    @GetMapping("/{id}")
    public ResponseEntity<OrderItem> getOrderItemById(@PathVariable Long id) {
        OrderItem item = orderItemService.getOrderItemById(id);
        return ResponseEntity.ok(item);
    }


    // 🟡 Cập nhật số lượng món
    @PutMapping("/{id}/quantity")
    public ResponseEntity<OrderItem> updateQuantity(@PathVariable Long id, @RequestParam int quantity) {
        OrderItem updated = orderItemService.updateQuantity(id, quantity);
        return ResponseEntity.ok(updated);
    }


    // 🔴 Xóa món khỏi đơn hàng
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrderItem(@PathVariable Long id) {
        orderItemService.deleteOrderItem(id);
        return ResponseEntity.ok("🗑️ Đã xóa món trong đơn hàng, ID: " + id);
    }
}
