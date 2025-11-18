package com.fooddelivery.delivery.dto.request;

public class DeliveryRequest {
    private Long orderId;
    private String droneId;

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
}
