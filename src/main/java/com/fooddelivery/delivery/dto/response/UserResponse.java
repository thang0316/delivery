package com.fooddelivery.delivery.dto.response;

import com.fooddelivery.delivery.entity.User.Role;

import lombok.Data;

@Data
public class UserResponse {
	private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private String phone;
    private Role role;
}
