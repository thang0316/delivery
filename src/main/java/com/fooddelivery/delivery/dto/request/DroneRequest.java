package com.fooddelivery.delivery.dto.request;

public class DroneRequest {
	private String model;
    private String status;
    private Double batteryLevel;
    private Double currentLatitude;
    private Double currentLongitude;
    
    private String restaurantId;

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

	public Double getBatteryLevel() {
		return batteryLevel;
	}

	public void setBatteryLevel(Double batteryLevel) {
		this.batteryLevel = batteryLevel;
	}

	public String getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(String restaurantId) {
		this.restaurantId = restaurantId;
	}

	public Double getCurrentLatitude() {
		return currentLatitude;
	}

	public void setCurrentLatitude(Double currentLatitude) {
		this.currentLatitude = currentLatitude;
	}

	public Double getCurrentLongitude() {
		return currentLongitude;
	}

	public void setCurrentLongitude(Double currentLongitude) {
		this.currentLongitude = currentLongitude;
	}
}
