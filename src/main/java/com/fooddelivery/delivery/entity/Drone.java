package com.fooddelivery.delivery.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "drones")
public class Drone {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    

    @Column(nullable = false)
    private String model; // Tên hoặc loại drone

    @Column(nullable = false)
    private String status; // "AVAILABLE", "DELIVERING", "CHARGING", "MAINTENANCE"

    private double batteryLevel; // phần trăm pin (0-100)

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant; // ID của nhà hàng mà drone thuộc về

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
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

	

    
    
}
