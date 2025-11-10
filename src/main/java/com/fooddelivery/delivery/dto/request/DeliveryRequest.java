package com.fooddelivery.delivery.dto.request;

public class DeliveryRequest {
    private Long orderId;
    private String droneId;
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
