package com.fooddelivery.delivery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import com.fooddelivery.delivery.dto.request.DeliveryRequest;
import com.fooddelivery.delivery.entity.*;
import com.fooddelivery.delivery.service.DeliveryService;

@RestController
@RequestMapping("/api/deliveries")
public class DeliveryController {

	@Autowired
    private DeliveryService deliveryService;

    @PostMapping
    public Delivery createDelivery(@RequestBody DeliveryRequest request) {
        return deliveryService.createDelivery(request);
    }

    @GetMapping
    public List<Delivery> getAllDeliveries() {
        return deliveryService.getAllDeliveries();
    }

    @GetMapping("/{id}")
    public Delivery getDeliveryById(@PathVariable String id) {
        return deliveryService.getDeliveryById(id);
    }

    @GetMapping("/order/{orderId}")
    public Delivery getDeliveryByOrderId(@PathVariable String orderId) {
        return deliveryService.getDeliveryByOrderId(orderId);
    }

    @PutMapping("/{id}/status")
    public Delivery updateDeliveryStatus(
            @PathVariable String id,
            @RequestParam String status) {
        return deliveryService.updateStatus(id, Delivery.DeliveryStatus.valueOf(status));
    }

    @DeleteMapping("/{id}")
    public void deleteDelivery(@PathVariable String id) {
        deliveryService.deleteDelivery(id);
    }
}
