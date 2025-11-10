package com.fooddelivery.delivery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fooddelivery.delivery.dto.request.DeliveryRequest;
import com.fooddelivery.delivery.entity.*;
import com.fooddelivery.delivery.repository.*;

@Service
public class DeliveryService {
    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DroneRepository droneRepository;

    // Tạo mới giao hàng
    public Delivery createDelivery(DeliveryRequest request) {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Drone drone = droneRepository.findById(request.getDroneId())
                .orElseThrow(() -> new RuntimeException("Drone not found"));

        Delivery delivery = new Delivery();
        delivery.setOrder(order);
        delivery.setDrone(drone);
        delivery.setCurrentLatitude(request.getCurrentLatitude());
        delivery.setCurrentLongitude(request.getCurrentLongitude());

        // Tạo delivery → đồng bộ trạng thái order
        delivery = deliveryRepository.save(delivery);
        order.setStatus(Order.OrderStatus.DELIVERING);
        orderRepository.save(order);

        return delivery;
    }

    // Lấy danh sách tất cả delivery
    public List<Delivery> getAllDeliveries() {
        return deliveryRepository.findAll();
    }

    // Lấy 1 delivery theo id
    public Delivery getDeliveryById(String id) {
        return deliveryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Delivery not found"));
    }
    
    public Delivery updateDelivery(String id, DeliveryRequest request) {
        Delivery delivery = getDeliveryById(id);

        // Luôn cập nhật vị trí
        delivery.setCurrentLatitude(request.getCurrentLatitude());
        delivery.setCurrentLongitude(request.getCurrentLongitude());

        return deliveryRepository.save(delivery);
    }

    // Cập nhật trạng thái delivery
    public Delivery updateStatus(String deliveryId, Delivery.DeliveryStatus status) {
        Delivery delivery = getDeliveryById(deliveryId);

        if (status == Delivery.DeliveryStatus.COMPLETED) {
            delivery.markCompleted();  // Cập nhật status + endTime
            delivery.getOrder().setStatus(Order.OrderStatus.COMPLETED);
        } else if (status == Delivery.DeliveryStatus.CANCELED) {
            delivery.markCanceled();
            delivery.getOrder().setStatus(Order.OrderStatus.CANCELED);
        } else if (status == Delivery.DeliveryStatus.DELIVERING) {
            delivery.setStatus(Delivery.DeliveryStatus.DELIVERING);
            delivery.getOrder().setStatus(Order.OrderStatus.DELIVERING);
        }

        orderRepository.save(delivery.getOrder());
        return deliveryRepository.save(delivery);
    }

    // Xóa delivery
    public void deleteDelivery(String id) {
        deliveryRepository.deleteById(id);
    }
}
