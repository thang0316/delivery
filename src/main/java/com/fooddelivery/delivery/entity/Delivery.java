package com.fooddelivery.delivery.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "deliveries")
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    // Liên kết với đơn hàng
    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    // Drone thực hiện giao hàng
    @ManyToOne
    @JoinColumn(name = "drone_id", nullable = false)
    private Drone drone;

    private LocalDateTime startTime;   // Thời gian bắt đầu giao
    private LocalDateTime endTime;     // Thời gian kết thúc giao
    private double currentLatitude;    // Vĩ độ hiện tại của drone
    private double currentLongitude;   // Kinh độ hiện tại của drone

    // ➕ Trạng thái giao hàng
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryStatus status = DeliveryStatus.DELIVERING;

    // Khi tạo mới, tự gán thời gian bắt đầu
    @PrePersist
    public void onCreate() {
        this.startTime = LocalDateTime.now();
        this.status = DeliveryStatus.DELIVERING;
    }

    // Khi cập nhật sang hoàn thành hoặc hủy
    public void markCompleted() {
        this.status = DeliveryStatus.COMPLETED;
        this.endTime = LocalDateTime.now();
    }

    public void markCanceled() {
        this.status = DeliveryStatus.CANCELED;
        this.endTime = LocalDateTime.now();
    }

    // Enum trạng thái giao hàng
    public enum DeliveryStatus {
        DELIVERING, // Đang giao
        COMPLETED,  // Giao thành công
        CANCELED    // Giao thất bại / bị hủy
    }

    // Getters & Setters
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
    }
    public Drone getDrone() {
        return drone;
    }
    public void setDrone(Drone drone) {
        this.drone = drone;
    }
    public LocalDateTime getStartTime() {
        return startTime;
    }
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    public LocalDateTime getEndTime() {
        return endTime;
    }
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    public double getCurrentLatitude() {
        return currentLatitude;
    }
    public void setCurrentLatitude(double currentLatitude) {
        this.currentLatitude = currentLatitude;
    }
    public double getCurrentLongitude() {
        return currentLongitude;
    }
    public void setCurrentLongitude(double currentLongitude) {
        this.currentLongitude = currentLongitude;
    }
    public DeliveryStatus getStatus() {
        return status;
    }
    public void setStatus(DeliveryStatus status) {
        this.status = status;
    }
}
