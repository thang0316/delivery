package com.fooddelivery.delivery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fooddelivery.delivery.dto.request.UserRequest;
import com.fooddelivery.delivery.dto.request.UserUpdateRequest;
import com.fooddelivery.delivery.dto.request.ChangePasswordRequest;
import com.fooddelivery.delivery.entity.User;
import com.fooddelivery.delivery.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private UserService userService;
	
	@PostMapping
	User createUser(@RequestBody UserRequest request) {
		return userService.createUser(request);
	}
	
	@GetMapping
	List<User> getAllUsers(){
		return userService.getUsers();
	}
	
	@GetMapping("/{userId}")
	User getUserById(@PathVariable String userId) {
		return userService.getUser(userId);
	}
	
	@PutMapping("/{userId}")
	User updateUser(@PathVariable String userId,@RequestBody UserUpdateRequest request) {
		return userService.updateUser(userId, request);
	}
	
	@DeleteMapping("/{userId}")
	String deleteUser(@PathVariable String userId) {
		userService.deleteUser(userId);
		return "User has been deleted";
	}
	
	@PostMapping("/change-password")
	String changePassword(@RequestBody ChangePasswordRequest request) {
		return userService.changePassword(request.getUserId(), request.getOldPassword(), request.getNewPassword());
	}
}
