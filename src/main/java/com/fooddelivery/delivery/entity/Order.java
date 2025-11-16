package com.fooddelivery.delivery.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Người đặt hàng
    @Column(nullable = false)
    private String customerName;
    
    @Column(nullable = false)
    private String customerPhone;
    
    @Column(nullable = false)
    private String customerAddress;

    // Tổng tiền
    @Column(nullable = false)
    private Double totalAmount;

    // Trạng thái đơn hàng
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status = OrderStatus.PENDING;

    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User customer;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<OrderItem> items;

    public enum OrderStatus {
        PENDING,     // Chưa xác nhận
        CONFIRMED,   // Đã xác nhận
        DELIVERING,  // Đang giao
        COMPLETED,   // Hoàn thành
        CANCELED     // Hủy
    }
    
    /**
     * Kiểm tra xem có thể chuyển sang trạng thái mới không
     * Admin có thể chuyển tự do
     */
    public boolean canTransitionTo(OrderStatus newStatus) {
        return true;
    }
    
    public boolean isCancelable() {
        return this.status == OrderStatus.PENDING || this.status == OrderStatus.DELIVERING;
    }

    // Getter & Setter giữ nguyên format cũ

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getCustomerPhone() { return customerPhone; }
    public void setCustomerPhone(String customerPhone) { this.customerPhone = customerPhone; }

    public String getCustomerAddress() { return customerAddress; }
    public void setCustomerAddress(String customerAddress) { this.customerAddress = customerAddress; }

    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public User getCustomer() { return customer; }
    public void setCustomer(User customer) { this.customer = customer; }

    public Restaurant getRestaurant() { return restaurant; }
    public void setRestaurant(Restaurant restaurant) { this.restaurant = restaurant; }

    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }
}
