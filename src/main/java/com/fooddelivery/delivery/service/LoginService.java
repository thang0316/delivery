package com.fooddelivery.delivery.service;

import com.fooddelivery.delivery.dto.request.LoginRequest;
import com.fooddelivery.delivery.entity.User;
import com.fooddelivery.delivery.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginService {

	@Autowired
    private  UserRepository userRepository;
	
	@Autowired
    private  PasswordEncoder passwordEncoder;

    public User login(LoginRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElse(null);

        if (user == null) return null;

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return null; // sai pass
        }

        return user; // đúng pass
    }
}
