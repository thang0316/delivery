package com.fooddelivery.delivery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.fooddelivery.delivery.entity.OrderItem;
import com.fooddelivery.delivery.service.OrderItemService;

@RestController
@RequestMapping("/api/order-items")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    // Thêm món ăn vào đơn hàng
    @PostMapping
    public OrderItem addOrderItem(
            @RequestParam Long orderId,
            @RequestParam Long menuItemId,
            @RequestParam int quantity) {
        return orderItemService.addOrderItem(orderId, menuItemId, quantity);
    }

    // Lấy danh sách món trong 1 đơn hàng
    @GetMapping("/order/{orderId}")
    public List<OrderItem> getItemsByOrder(@PathVariable Long orderId) {
        return orderItemService.getItemsByOrder(orderId);
    }

    // Lấy chi tiết 1 món
    @GetMapping("/{id}")
    public OrderItem getOrderItemById(@PathVariable Long id) {
        return orderItemService.getOrderItemById(id);
    }

    // Cập nhật số lượng
    @PutMapping("/{id}/quantity")
    public OrderItem updateQuantity(@PathVariable Long id, @RequestParam int quantity) {
        return orderItemService.updateQuantity(id, quantity);
    }

    // Xóa món khỏi đơn hàng
    @DeleteMapping("/{id}")
    public void deleteOrderItem(@PathVariable Long id) {
        orderItemService.deleteOrderItem(id);
    }
}
