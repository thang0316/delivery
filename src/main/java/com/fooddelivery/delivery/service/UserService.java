package com.fooddelivery.delivery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fooddelivery.delivery.dto.request.UserCreationRequest;
import com.fooddelivery.delivery.dto.request.UserUpdateRequest;
import com.fooddelivery.delivery.entity.User;
import com.fooddelivery.delivery.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public User createUser(UserCreationRequest request){
		User user = new User();
		user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setRole(request.getRole());
		
		
		return userRepository.save(user);
	}
	
	public User getUser(String userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found"));
	}
	
	public List<User> getUsers(){
		return userRepository.findAll();
	}
	
	public User updateUser(String userId, UserUpdateRequest request) {
		User user = getUser(userId);
		
		user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setRole(request.getRole());
		
		return userRepository.save(user);
	}
	
	public void deleteUser(String userId) {
		userRepository.deleteById(userId);
	}
	
}
