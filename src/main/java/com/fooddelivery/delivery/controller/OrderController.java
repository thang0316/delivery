package com.fooddelivery.delivery.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.fooddelivery.delivery.dto.request.OrderRequest;
import com.fooddelivery.delivery.entity.Order;
import com.fooddelivery.delivery.service.OrderService;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*") //  Cho phép gọi từ frontend HTML / localhost
public class OrderController {

    @Autowired
    private OrderService orderService;


    //  Tạo đơn hàng mới
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest request) {
        Order created = orderService.createOrder(request);
        return ResponseEntity.ok(created);
    }


    //  Lấy danh sách tất cả đơn hàng
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getOrders();
        return ResponseEntity.ok(orders);
    }


    //  Lấy đơn hàng theo ID
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
        Order order = orderService.getOrder(orderId);
        return ResponseEntity.ok(order);
    }


    //  Lấy danh sách đơn hàng của 1 người dùng
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByCustomer(@PathVariable String userId) {
        List<Order> orders = orderService.getOrdersByCustomer(userId);
        return ResponseEntity.ok(orders);
    }


    //  Cập nhật trạng thái đơn hàng
    @PutMapping("/{orderId}/status")
    public Order updateStatus(@PathVariable Long orderId, @RequestParam Order.OrderStatus status) {
    	try {
            return orderService.updateStatus(orderId, status);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }


    //  Xóa đơn hàng
    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.ok("🗑️ Đã xóa đơn hàng ID: " + orderId);
    }
}
