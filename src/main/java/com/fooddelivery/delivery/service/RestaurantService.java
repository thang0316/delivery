package com.fooddelivery.delivery.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fooddelivery.delivery.entity.Restaurant;
import com.fooddelivery.delivery.entity.RoleName;
import com.fooddelivery.delivery.entity.User;
import com.fooddelivery.delivery.repository.RestaurantRepository;
import com.fooddelivery.delivery.repository.UserRepository;
import com.fooddelivery.delivery.dto.request.RestaurantCreationRequest;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UserRepository userRepository;

    // Tạo nhà hàng
    public Restaurant createRestaurant(RestaurantCreationRequest request) {
        User owner = userRepository.findById(request.getOwner())
            .orElseThrow(() -> new RuntimeException("User không tồn tại"));

        if (owner.getRole() == null || owner.getRole().getName() != RoleName.RESTAURANT) {
            throw new RuntimeException("User không có phân quyền RESTAURANT_OWNER");
        }

        Restaurant restaurant = new Restaurant();
        restaurant.setOwner(owner);  // sửa lỗi ownerUser
        restaurant.setName(request.getName());
        restaurant.setAddress(request.getAddress());
        restaurant.setPhone(request.getPhone());
        restaurant.setActive(request.getActive());
        restaurant.setCreatedAt(LocalDateTime.now());
        restaurant.setRating(5.0);  // mặc định 5.0

        return restaurantRepository.save(restaurant);
    }

    // Lấy tất cả nhà hàng
    public List<Restaurant> getAllRestaurant() {
        return restaurantRepository.findAll();
    }

    // Lấy nhà hàng theo ID
    public Optional<Restaurant> getRestaurantById(String id) {
        return restaurantRepository.findById(id);
    }

    // Cập nhật nhà hàng
    public Restaurant updateRestaurant(String id, RestaurantCreationRequest request) {
        Restaurant restaurant = restaurantRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy nhà hàng với ID " + id));

        User owner = userRepository.findById(request.getOwner())
            .orElseThrow(() -> new RuntimeException("User không tồn tại"));

        if (owner.getRole() == null || owner.getRole().getName() != RoleName.RESTAURANT) {
            throw new RuntimeException("User không có phân quyền RESTAURANT_OWNER");
        }

        restaurant.setName(request.getName());
        restaurant.setAddress(request.getAddress());
        restaurant.setPhone(request.getPhone());
        restaurant.setOwner(owner);
        restaurant.setActive(request.getActive());

        return restaurantRepository.save(restaurant);
    }

    // Xóa nhà hàng
    public void deleteRestaurant(String id) {
        restaurantRepository.deleteById(id);
    }

    // Cập nhật trạng thái hoạt động
    public Restaurant updateRestaurantStatus(String id, Boolean active) {
        Restaurant restaurant = restaurantRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy nhà hàng với ID " + id));
        
        restaurant.setActive(active);
        return restaurantRepository.save(restaurant);
    }
}
