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

    @Autowired
    private DroneService droneService;

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

        // ⭐ Di chuyển drone đến địa chỉ khách hàng (giả lập - TỪ TỪ)
        if(order.getDeliveryLatitude() != null && order.getDeliveryLongitude() != null) {
            droneService.moveDroneToCustomerAsync(drone.getId(), 
                order.getDeliveryLatitude(), 
                order.getDeliveryLongitude());
        }

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
            // ⚠️ KHÔNG set AVAILABLE ngay - để animation callback xử lý sau khi bay về xong
            // delivery.getDrone().setStatus(Drone.DroneStatus.AVAILABLE);
            
            // ⭐ Drone bay về nhà hàng (giả lập - TỪ TỪ), sau đó tự động set AVAILABLE
            droneService.moveDroneToRestaurantAsync(delivery.getDrone().getId(), true);
            
        } else if (status == Delivery.DeliveryStatus.CANCELED) {
            delivery.markCanceled();
            delivery.getOrder().setStatus(Order.OrderStatus.CANCELED);
            // ⚠️ KHÔNG set AVAILABLE ngay - để animation callback xử lý sau khi bay về xong
            // delivery.getDrone().setStatus(Drone.DroneStatus.AVAILABLE);
            
            // ⭐ Drone bay về nhà hàng (giả lập - TỪ TỪ), sau đó tự động set AVAILABLE
            droneService.moveDroneToRestaurantAsync(delivery.getDrone().getId(), true);
            
        } else if (status == Delivery.DeliveryStatus.DELIVERING) {
            delivery.setStatus(Delivery.DeliveryStatus.DELIVERING);
            delivery.getOrder().setStatus(Order.OrderStatus.DELIVERING);
            delivery.getDrone().setStatus(Drone.DroneStatus.DELIVERING); // Drone đang giao
            
            // ⭐ Drone bay đến khách hàng (giả lập - TỪ TỪ)
            Order order = delivery.getOrder();
            if(order.getDeliveryLatitude() != null && order.getDeliveryLongitude() != null) {
                droneService.moveDroneToCustomerAsync(delivery.getDrone().getId(),
                    order.getDeliveryLatitude(),
                    order.getDeliveryLongitude());
            }
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
