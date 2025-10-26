package com.fooddelivery.delivery.dto.response;

import java.time.LocalDateTime;

public class UserResponse {
    private String id;
    private String username;
    private String fullName;
    private String email;
    private String phone;
    private String roleName;
    private LocalDateTime createdAt;


    // Getters
    public String getId() { return id; }
    public String getUsername() { return username; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getRoleName() { return roleName; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
