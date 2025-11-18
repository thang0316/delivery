package com.fooddelivery.delivery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fooddelivery.delivery.dto.request.DeliveryRequest;
import com.fooddelivery.delivery.entity.*;
import com.fooddelivery.delivery.entity.Drone.DroneStatus;
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

        // Kiểm tra drone có sẵn sàng không
        if (drone.getStatus() != Drone.DroneStatus.AVAILABLE) {
            throw new RuntimeException("Drone hiện không sẵn sàng để giao hàng!");
        }

        Delivery delivery = new Delivery();
        delivery.setOrder(order);
        delivery.setDrone(drone);

        // Tạo delivery → đồng bộ trạng thái order + drone
        delivery = deliveryRepository.save(delivery);
        order.setStatus(Order.OrderStatus.DELIVERING);
        orderRepository.save(order);

        // Cập nhật drone đang giao
        drone.setStatus(DroneStatus.DELIVERING);
        droneRepository.save(drone);

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

    // Lấy delivery theo order id
    public Delivery getDeliveryByOrderId(String orderId) {
        try {
            Long orderIdLong = Long.parseLong(orderId);
            return deliveryRepository.findByOrder_Id(orderIdLong)
                    .orElseThrow(() -> new RuntimeException("Delivery not found for order"));
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid order ID");
        }
    }

    // Cập nhật trạng thái delivery
    public Delivery updateStatus(String deliveryId, Delivery.DeliveryStatus status) {
        Delivery delivery = getDeliveryById(deliveryId);

        if (status == Delivery.DeliveryStatus.COMPLETED) {
            delivery.markCompleted(); 
            delivery.getOrder().setStatus(Order.OrderStatus.COMPLETED);
            delivery.getDrone().setStatus(Drone.DroneStatus.AVAILABLE); // Drone sẵn sàng
        } else if (status == Delivery.DeliveryStatus.CANCELED) {
            delivery.markCanceled();
            delivery.getOrder().setStatus(Order.OrderStatus.CANCELED);
            delivery.getDrone().setStatus(Drone.DroneStatus.AVAILABLE); // Drone sẵn sàng
        } else if (status == Delivery.DeliveryStatus.DELIVERING) {
            delivery.setStatus(Delivery.DeliveryStatus.DELIVERING);
            delivery.getOrder().setStatus(Order.OrderStatus.DELIVERING);
            delivery.getDrone().setStatus(Drone.DroneStatus.DELIVERING); // Drone đang giao
        }

        orderRepository.save(delivery.getOrder());
        droneRepository.save(delivery.getDrone()); // Lưu drone
        return deliveryRepository.save(delivery);
    }


    // Xóa delivery
    public void deleteDelivery(String id) {
        deliveryRepository.deleteById(id);
    }
}
