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
   
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status = PaymentStatus.PENDING;
    
 // Enum trạng thái thanh toán
    public enum PaymentStatus {
        PENDING,    // Chưa thanh toán
        COMPLETED,  // Thanh toán thành công
        FAILED,     // Thanh toán không thành công
        CANCELED    // Thanh toán bị hủy
    }
    
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
		if (order != null && this.amount == null) {
            this.amount = order.getTotalAmount(); // Đồng bộ amount với order
        }
	}
	
	public PaymentStatus getStatus() {
		return status;
	}
	public void setStatus(PaymentStatus status) {
		this.status = status;
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
	
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
    
    
}
