package com.fooddelivery.delivery.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity

public class Payment {
	@Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    // Mỗi Payment thuộc về một đơn hàng
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    private String method; // Cash, CreditCard, EWallet...
    private Double amount;
    private String status; // Pending, Completed, Failed
    private LocalDateTime createdAt = LocalDateTime.now();
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
    
    
}
