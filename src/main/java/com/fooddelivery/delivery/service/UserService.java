package com.fooddelivery.delivery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fooddelivery.delivery.dto.request.UserRequest;
import com.fooddelivery.delivery.dto.request.UserUpdateRequest;
import com.fooddelivery.delivery.entity.Role;
import com.fooddelivery.delivery.entity.User;
import com.fooddelivery.delivery.repository.RoleRepository;
import com.fooddelivery.delivery.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository; // ✅ thêm @Autowired

    // ➕ Thêm user
    public User createUser(UserRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword()); // ⚠️ sau này nên mã hóa BCrypt
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());

        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRole(role);

        return userRepository.save(user);
    }

    // 🔍 Lấy user theo ID
    public User getUser(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // 📋 Danh sách user
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    // 🔄 Cập nhật user
    public User updateUser(String userId, UserUpdateRequest request) {
        User user = getUser(userId);

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());

        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRole(role);

        return userRepository.save(user);
    }

    // ❌ Xóa user
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }
}
