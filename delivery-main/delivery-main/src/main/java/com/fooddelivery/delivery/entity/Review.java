package com.fooddelivery.delivery.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "reviews")
public class Review {
	@Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    // Người dùng viết đánh giá
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Đánh giá cho đơn hàng cụ thể
    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    private Integer rating; // Số sao: 1–5
    private String comment; // Nội dung nhận xét
    private LocalDateTime createdAt = LocalDateTime.now();
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Integer getRating() {
		return rating;
	}
	public void setRating(Integer rating) {
		this.rating = rating;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
    
    
}
