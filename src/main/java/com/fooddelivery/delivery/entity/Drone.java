package com.fooddelivery.delivery.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "drones")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Drone {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String model;

    public enum DroneStatus {
        AVAILABLE,   // Sẵn sàng nhận đơn
        DELIVERING,  // Đang giao hàng
        CHARGING,    // Đang sạc
        OFFLINE      // Ngoại tuyến
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DroneStatus status = DroneStatus.AVAILABLE;

    private double batteryLevel;

    private double currentLatitude;    // Vĩ độ hiện tại của drone
    private double currentLongitude;   // Kinh độ hiện tại của drone

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Restaurant restaurant;

    // ===== Getter & Setter =====
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public DroneStatus getStatus() {
		return status;
	}

	public void setStatus(DroneStatus status) {
		this.status = status;
	}

	public double getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(double batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
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
