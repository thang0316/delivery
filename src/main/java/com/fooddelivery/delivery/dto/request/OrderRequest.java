package com.fooddelivery.delivery.dto.request;

import java.util.List;

public class OrderRequest {
	private String userId;
    private String restaurantId;
    private String customerName;
    private String customerPhone;
    private String customerAddress;
    private Double deliveryLatitude;
    private Double deliveryLongitude;
    private List<OrderItemRequest> items;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(String restaurantId) {
		this.restaurantId = restaurantId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerPhone() {
		return customerPhone;
	}
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}
	public String getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}
	public Double getDeliveryLatitude() {
		return deliveryLatitude;
	}
	public void setDeliveryLatitude(Double deliveryLatitude) {
		this.deliveryLatitude = deliveryLatitude;
	}
	public Double getDeliveryLongitude() {
		return deliveryLongitude;
	}
	public void setDeliveryLongitude(Double deliveryLongitude) {
		this.deliveryLongitude = deliveryLongitude;
	}
	public List<OrderItemRequest> getItems() {
		return items;
	}
	public void setItems(List<OrderItemRequest> items) {
		this.items = items;
	}
	
    
}

