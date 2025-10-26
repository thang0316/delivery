package com.fooddelivery.delivery.dto.request;

public class MenuItemRequest {
	private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private String restaurantId;
    private Boolean available;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getRestaurantId() {
		return restaurantId;
	}	
	public void setRestaurantId(String restaurantId) {
		this.restaurantId = restaurantId;
	}
	public Boolean getAvailable() {
		return available;
	}
	public void setAvailable(Boolean available) {
		this.available = available;
	}
    
    
}
