package com.fooddelivery.delivery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fooddelivery.delivery.dto.request.UserRequest;
import com.fooddelivery.delivery.dto.request.UserUpdateRequest;
import com.fooddelivery.delivery.entity.Role;
import com.fooddelivery.delivery.entity.User;
import com.fooddelivery.delivery.entity.Restaurant;
import com.fooddelivery.delivery.repository.RoleRepository;
import com.fooddelivery.delivery.repository.UserRepository;
import com.fooddelivery.delivery.repository.RestaurantRepository;
import com.fooddelivery.delivery.repository.OrderRepository;
import com.fooddelivery.delivery.repository.PaymentRepository;
import com.fooddelivery.delivery.repository.ReviewRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private RestaurantRepository restaurantRepository;
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private PaymentRepository paymentRepository;
    
    @Autowired
    private ReviewRepository reviewRepository;
    
    
    private final PasswordEncoder passwordEncoder = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();

    // Thêm user
    public User createUser(UserRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());

        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRole(role);

        User savedUser = userRepository.save(user);
        
        // Nếu role là RESTAURANT (roleId = 3), tự động tạo nhà hàng
        if(request.getRoleId() == 3) {
            Restaurant restaurant = new Restaurant();
            // Dùng tên nhà hàng từ request nếu có, không thì dùng tên người dùng
            restaurant.setName(request.getRestaurantName() != null && !request.getRestaurantName().isEmpty() 
                ? request.getRestaurantName() 
                : request.getFirstName() + " " + request.getLastName());
            // Dùng địa chỉ từ request nếu có, không thì mặc định
            restaurant.setAddress(request.getRestaurantAddress() != null && !request.getRestaurantAddress().isEmpty()
                ? request.getRestaurantAddress()
                : "Chưa cập nhật");
            restaurant.setLatitude(request.getRestaurantLatitude());
            restaurant.setLongitude(request.getRestaurantLongitude());
            restaurant.setPhone(request.getPhone());
            restaurant.setOwner(savedUser);
            restaurant.setActive(true);
            restaurant.setRating(5.0);
            restaurantRepository.save(restaurant);
        }
        
        return savedUser;
    }

    //  Lấy user theo ID
    public User getUser(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    //  Danh sách user
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    //  Cập nhật user
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

    //  Xóa user
    public void deleteUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng!"));
        
        // Xóa tất cả Review của user
        reviewRepository.findByUserId(userId).forEach(review -> {
            reviewRepository.delete(review);
        });
        
        // Xóa tất cả Order của user
        orderRepository.findByCustomer_Id(userId).forEach(order -> {
            // Xóa Review của order
            reviewRepository.findByOrderId(order.getId()).ifPresent(review -> {
                reviewRepository.delete(review);
            });
            // Xóa Payment của order
            paymentRepository.findByOrderId(order.getId()).forEach(payment -> {
                paymentRepository.delete(payment);
            });
            // Xóa OrderItems
            if (order.getItems() != null && !order.getItems().isEmpty()) {
                order.getItems().clear();
                orderRepository.save(order);
            }
            orderRepository.delete(order);
        });
        
        // Xóa Restaurant nếu user là owner
        restaurantRepository.findAll().stream()
                .filter(r -> r.getOwner() != null && r.getOwner().getId().equals(userId))
                .forEach(restaurant -> {
                    restaurantRepository.delete(restaurant);
                });
        
        // Xóa user
        userRepository.deleteById(userId);
    }

    // Đổi mật khẩu
    public String changePassword(String userId, String oldPassword, String newPassword) {
        User user = getUser(userId);

        // Kiểm tra mật khẩu cũ
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("Mật khẩu hiện tại không đúng!");
        }

        // Cập nhật mật khẩu mới
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return "Đổi mật khẩu thành công!";
    }
}
