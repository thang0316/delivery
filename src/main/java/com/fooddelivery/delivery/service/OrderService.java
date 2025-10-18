package com.fooddelivery.delivery.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fooddelivery.delivery.entity.Order;
import com.fooddelivery.delivery.repository.OrderRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Order createOrder(Order request) {
        Order order = new Order();
        order.setOrderTime(request.getOrderTime());
        order.setTotalAmount(request.getTotalAmount());
        order.setStatus(request.getStatus());
        order.setCustomer(request.getCustomer());
        order.setRestaurant(request.getRestaurant());
        order.setDrone(request.getDrone());
        return orderRepository.save(order);
    }

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    public Order getOrder(String id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public Order updateOrder(String id, Order request) {
        Order existing = getOrder(id);
        existing.setOrderTime(request.getOrderTime());
        existing.setTotalAmount(request.getTotalAmount());
        existing.setStatus(request.getStatus());
        existing.setCustomer(request.getCustomer());
        existing.setRestaurant(request.getRestaurant());
        existing.setDrone(request.getDrone());
        return orderRepository.save(existing);
    }

    public void deleteOrder(String id) {
        orderRepository.deleteById(id);
    }
}
