package com.fooddelivery.delivery.dto.response;

import java.time.LocalDateTime;

public class RestaurantResponse {
	private String id;
    private String name;
    private String address;
    private String phone;
    private String ownerId;
    private Double rating;      // Rating trung bình
    private Boolean active;
    private LocalDateTime createdAt;
    
	public RestaurantResponse(String id, String name, String address, String phone, String ownerId, Double rating,
			Boolean active, LocalDateTime createdAt) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.ownerId = ownerId;
		this.rating = rating;
		this.active = active;
		this.createdAt = createdAt;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
    
	
    
}
