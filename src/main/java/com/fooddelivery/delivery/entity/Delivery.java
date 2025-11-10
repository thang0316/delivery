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
    
    
    
    
}
