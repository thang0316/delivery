package com.fooddelivery.delivery.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fooddelivery.delivery.dto.request.DroneRequest;
import com.fooddelivery.delivery.entity.Drone;
import com.fooddelivery.delivery.entity.Restaurant;
import com.fooddelivery.delivery.repository.DroneRepository;
import com.fooddelivery.delivery.repository.RestaurantRepository;

@Service
public class DroneService {

    @Autowired
    private DroneRepository droneRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;
    
    public Drone createDrone(DroneRequest request) {
    	Optional<Restaurant> restaurantOptional = restaurantRepository.findById(request.getRestaurantId());
    	if(restaurantOptional.isEmpty()) {
    		throw new RuntimeException("Khong tim thay nha hang id: " + request.getRestaurantId());
    	}
    	
    	Restaurant restaurant = restaurantOptional.get();

        Drone drone = new Drone();
        drone.setModel(request.getModel());
        drone.setStatus(request.getStatus());
        drone.setBatteryLevel(request.getBatteryLevel());
        
        drone.setRestaurant(restaurant);

        return droneRepository.save(drone);
    }
    
    public List<Drone> getDronesByRestaurant(String restaurantId) {
        // Kiểm tra xem nhà hàng có tồn tại không
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhà hàng với id: " + restaurantId));

        // Lấy danh sách drone thuộc nhà hàng đó
        return droneRepository.findByRestaurant(restaurant);
    }
    
    // danh sach drone
    public List<Drone> getAllDrones() {
        return droneRepository.findAll();
    }
    
 // Lấy drone theo ID
    public Drone getDroneById(String id) {
        return droneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Drone not found with id: " + id));
    }
    
 // Cập nhật drone
    public Drone updateDrone(String id, DroneRequest request) {
        Drone drone = getDroneById(id);
        drone.setModel(request.getModel());
        drone.setStatus(request.getStatus());
        drone.setBatteryLevel(request.getBatteryLevel());
        

        if (request.getRestaurantId() != null) {
            Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
                    .orElseThrow(() -> new RuntimeException("Restaurant not found"));
            drone.setRestaurant(restaurant);
        }

        return droneRepository.save(drone);
    }

    // Xóa drone
    public void deleteDrone(String id) {
        droneRepository.deleteById(id);
    }
}
