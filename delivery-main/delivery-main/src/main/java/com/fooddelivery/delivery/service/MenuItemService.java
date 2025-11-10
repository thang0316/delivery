package com.fooddelivery.delivery.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fooddelivery.delivery.dto.request.MenuItemRequest;
import com.fooddelivery.delivery.entity.MenuItem;
import com.fooddelivery.delivery.entity.Restaurant;
import com.fooddelivery.delivery.repository.MenuItemRepository;
import com.fooddelivery.delivery.repository.RestaurantRepository;

@Service
public class MenuItemService {
	@Autowired
	private MenuItemRepository menuItemRepository;
	
	@Autowired
    private RestaurantRepository restaurantRepository;
	

    //  Tạo món ăn mới
    public MenuItem createMenuItem(MenuItemRequest request) {
        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhà hàng với ID: " + request.getRestaurantId()));

        MenuItem item = new MenuItem();
        item.setName(request.getName());
        item.setDescription(request.getDescription());
        item.setPrice(request.getPrice());
        item.setImageUrl(request.getImageUrl());
        item.setRestaurant(restaurant);
        item.setAvailable(request.getAvailable());
        item.setCreatedAt(LocalDateTime.now());

        return menuItemRepository.save(item);
    }

    //  Lấy tất cả món ăn
    public List<MenuItem> getAllMenuItems() {
        return menuItemRepository.findAll();
    }

    //  Lấy món ăn theo ID
    public MenuItem getMenuItemById(Long id) {
        return menuItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy món ăn với ID: " + id));
    }

    //  Lấy danh sách món ăn theo ID nhà hàng
    public List<MenuItem> getMenuItemsByRestaurant(String restaurantId) {
        return menuItemRepository.findByRestaurantId(restaurantId);
    }

    //  Cập nhật món ăn
    public MenuItem updateMenuItem(Long id, MenuItemRequest request) {
        MenuItem item = getMenuItemById(id);
        item.setName(request.getName());
        item.setDescription(request.getDescription());
        item.setPrice(request.getPrice());
        item.setImageUrl(request.getImageUrl());
        item.setAvailable(request.getAvailable());
        return menuItemRepository.save(item);
    }

    //  Xóa món ăn
    public void deleteMenuItem(Long id) {
        if (!menuItemRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy món ăn để xóa với ID: " + id);
        }
        menuItemRepository.deleteById(id);
    }
}
