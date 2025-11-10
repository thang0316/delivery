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
        delivery.setStartTime(request.getStartTime());
        delivery.setEndTime(request.getEndTime());
        
        delivery.setCurrentLatitude(request.getCurrentLatitude());
        delivery.setCurrentLongitude(request.getCurrentLongitude());

        return deliveryRepository.save(delivery);
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

    // Cập nhật thông tin giao hàng
    public Delivery updateDelivery(String id, DeliveryRequest request) {
        Delivery delivery = getDeliveryById(id);

        delivery.setStartTime(request.getStartTime());
        delivery.setCurrentLatitude(request.getCurrentLatitude());
        delivery.setCurrentLongitude(request.getCurrentLongitude());
        delivery.setEndTime(request.getEndTime());

        return deliveryRepository.save(delivery);
    }

    // Xóa
    public void deleteDelivery(String id) {
        deliveryRepository.deleteById(id);
    }
}
