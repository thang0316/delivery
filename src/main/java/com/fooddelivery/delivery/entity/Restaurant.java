package com.fooddelivery.delivery.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "restaurants")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 255, nullable = false)
    private String address;

    private Double latitude;   // Vĩ độ vị trí nhà hàng
    private Double longitude;  // Kinh độ vị trí nhà hàng

    @Column(length = 20, nullable = false)
    private String phone;

    @OneToOne
    @JoinColumn(name = "owner_id", nullable = false)
    @JsonIgnoreProperties({"password", "email", "phone", "role", "createdAt"})
    // ⭐ ẨN field nhạy cảm → Giữ lại owner.id để frontend sử dụng
    private User owner;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private Double rating = 5.0;

    @Column(nullable = false)
    private Boolean active = true;

    // ===== Getter & Setter =====
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
}
