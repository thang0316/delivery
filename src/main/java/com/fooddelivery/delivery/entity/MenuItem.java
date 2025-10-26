package com.fooddelivery.delivery.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
public class MenuItem {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // tự tăng số nguyên
    private Long id;
	
	@Column(nullable = false, length = 255)
    private String name;
	
	@Column(length = 1000)
    private String description;
	
	@Column(nullable = false)
    private Double price;
	
	@Column(length = 255)
    private String imageUrl;
	
	@ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;
	
	@Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
	
	@Column(nullable = false)
    private Boolean available = true;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}
	
	
	
	
}
