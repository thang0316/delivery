package com.fooddelivery.delivery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.fooddelivery.delivery.dto.request.OrderRequest;
import com.fooddelivery.delivery.entity.Order;
import com.fooddelivery.delivery.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Tạo đơn hàng mới
    @PostMapping
    public Order createOrder(@RequestBody OrderRequest request) {
        return orderService.createOrder(request);
    }

    // Lấy danh sách tất cả đơn hàng
    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getOrders();
    }

    // Lấy đơn hàng theo ID
    @GetMapping("/{orderId}")
    public Order getOrderById(@PathVariable Long orderId) {
        return orderService.getOrder(orderId);
    }

    // Lấy danh sách đơn hàng của 1 người dùng
    @GetMapping("/user/{userId}")
    public List<Order> getOrdersByCustomer(@PathVariable String userId) {
        return orderService.getOrdersByCustomer(userId);
    }

    @PutMapping("/{orderId}/status")
    public Order updateStatus(@PathVariable Long orderId, @RequestParam Order.OrderStatus status) {
        try {
            return orderService.updateStatus(orderId, status);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    // Xóa đơn hàng
    @DeleteMapping("/{orderId}")
    public void deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
    }
}
