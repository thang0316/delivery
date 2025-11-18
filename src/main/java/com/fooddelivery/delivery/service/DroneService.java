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
        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhà hàng id: " + request.getRestaurantId()));

        Drone drone = new Drone();
        drone.setModel(request.getModel());
        // Nếu request truyền status dưới dạng String, convert sang enum
        if(request.getStatus() != null) {
            drone.setStatus(Drone.DroneStatus.valueOf(request.getStatus())); 
        } else {
            drone.setStatus(Drone.DroneStatus.AVAILABLE); // mặc định
        }
        drone.setBatteryLevel(request.getBatteryLevel());
        
        // Thiết lập vị trí mặc định nếu có
        if(request.getCurrentLatitude() != null) {
            drone.setCurrentLatitude(request.getCurrentLatitude());
        }
        if(request.getCurrentLongitude() != null) {
            drone.setCurrentLongitude(request.getCurrentLongitude());
        }
        
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
        
        // Cập nhật status
        if(request.getStatus() != null && !request.getStatus().isEmpty()) {
            try {
                drone.setStatus(Drone.DroneStatus.valueOf(request.getStatus()));
            } catch (IllegalArgumentException e) {
                // Fallback: mapping enum cũ sang enum mới
                String newStatus = request.getStatus();
                if("BUSY".equals(newStatus) || "DELIVERING".equals(newStatus)) {
                    drone.setStatus(Drone.DroneStatus.CHARGING);
                } else if("OFFLINE".equals(newStatus)) {
                    drone.setStatus(Drone.DroneStatus.OFFLINE);
                } else {
                    throw new RuntimeException("Invalid status: " + request.getStatus());
                }
            }
        }
        
        // Cập nhật batteryLevel nếu được cung cấp
        if(request.getBatteryLevel() != null && request.getBatteryLevel() >= 0) {
            drone.setBatteryLevel(request.getBatteryLevel());
        }

        return droneRepository.save(drone);
    }


    // Cập nhật vị trí drone
    public Drone updateDroneLocation(String id, double latitude, double longitude) {
        Drone drone = getDroneById(id);
        drone.setCurrentLatitude(latitude);
        drone.setCurrentLongitude(longitude);
        return droneRepository.save(drone);
    }

    // Xóa drone
    public void deleteDrone(String id) {
        droneRepository.deleteById(id);
    }
}
