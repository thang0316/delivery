package com.fooddelivery.delivery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fooddelivery.delivery.dto.request.UserCreationRequest;
import com.fooddelivery.delivery.entity.User;
import com.fooddelivery.delivery.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	public User createRequest(UserCreationRequest request){
		User user = new User();
		user.setUsername(request.getUsername());
		user.setPassword(request.getPassword());
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		user.setDob(request.getDob());
		
		return userRepository.save(user);	
	}
	
}
