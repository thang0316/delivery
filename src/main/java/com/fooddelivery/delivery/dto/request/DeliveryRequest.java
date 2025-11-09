package com.fooddelivery.delivery.dto.request;

import java.time.LocalDateTime;

public class DeliveryRequest {
	private Long orderId;
    private String droneId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double currentLatitude;
    private double currentLongitude;
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getDroneId() {
		return droneId;
	}
	public void setDroneId(String droneId) {
		this.droneId = droneId;
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
