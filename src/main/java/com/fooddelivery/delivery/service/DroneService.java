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
        
        // ⭐ Thiết lập vị trí mặc định = vị trí nhà hàng
        if(request.getCurrentLatitude() != null && request.getCurrentLongitude() != null) {
            drone.setCurrentLatitude(request.getCurrentLatitude());
            drone.setCurrentLongitude(request.getCurrentLongitude());
        } else if(restaurant.getLatitude() != null && restaurant.getLongitude() != null) {
            // Nếu không truyền vị trí, lấy vị trí nhà hàng
            drone.setCurrentLatitude(restaurant.getLatitude());
            drone.setCurrentLongitude(restaurant.getLongitude());
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

    // ⭐ Di chuyển drone đến vị trí nhà hàng (giả lập - từ từ)
    public void moveDroneToRestaurantAsync(String droneId, boolean setAvailableAfter) {
        Drone drone = getDroneById(droneId);
        Restaurant restaurant = drone.getRestaurant();
        
        if(restaurant.getLatitude() == null || restaurant.getLongitude() == null) {
            throw new RuntimeException("Nhà hàng chưa có tọa độ vị trí!");
        }
        
        double targetLat = restaurant.getLatitude();
        double targetLng = restaurant.getLongitude();
        
        // Chạy animation trong thread riêng
        new Thread(() -> {
            animateDroneMovement(droneId, targetLat, targetLng);
            
            // Sau khi bay về xong, set status = AVAILABLE
            if(setAvailableAfter) {
                try {
                    Drone d = getDroneById(droneId);
                    d.setStatus(Drone.DroneStatus.AVAILABLE);
                    droneRepository.save(d);
                    System.out.println("✅ Drone " + droneId + " đã về nhà hàng và sẵn sàng!");
                } catch(Exception e) {
                    System.err.println("❌ Lỗi cập nhật status: " + e.getMessage());
                }
            }
        }).start();
    }
    
    // ⭐ Di chuyển drone đến vị trí nhà hàng (tức thì - cho API backward compatibility)
    public Drone moveDroneToRestaurant(String droneId) {
        Drone drone = getDroneById(droneId);
        Restaurant restaurant = drone.getRestaurant();
        
        if(restaurant.getLatitude() != null && restaurant.getLongitude() != null) {
            drone.setCurrentLatitude(restaurant.getLatitude());
            drone.setCurrentLongitude(restaurant.getLongitude());
            return droneRepository.save(drone);
        } else {
            throw new RuntimeException("Nhà hàng chưa có tọa độ vị trí!");
        }
    }

    // ⭐ Di chuyển drone đến địa chỉ khách hàng (giả lập - từ từ)
    public void moveDroneToCustomerAsync(String droneId, double deliveryLat, double deliveryLng) {
        // Chạy animation trong thread riêng
        new Thread(() -> animateDroneMovement(droneId, deliveryLat, deliveryLng)).start();
    }
    
    // ⭐ Di chuyển drone đến địa chỉ khách hàng (tức thì - cho API backward compatibility)
    public Drone moveDroneToCustomer(String droneId, double deliveryLat, double deliveryLng) {
        Drone drone = getDroneById(droneId);
        drone.setCurrentLatitude(deliveryLat);
        drone.setCurrentLongitude(deliveryLng);
        return droneRepository.save(drone);
    }
    
    // 🎬 Animation: Di chuyển drone từ từ theo từng bước
    private void animateDroneMovement(String droneId, double targetLat, double targetLng) {
        try {
            Drone drone = getDroneById(droneId);
            double startLat = drone.getCurrentLatitude();
            double startLng = drone.getCurrentLongitude();
            
            int steps = 20; // Số bước di chuyển
            int delayMs = 500; // Delay giữa mỗi bước (0.5 giây)
            
            for(int i = 1; i <= steps; i++) {
                double progress = (double) i / steps;
                
                // Linear interpolation
                double newLat = startLat + (targetLat - startLat) * progress;
                double newLng = startLng + (targetLng - startLng) * progress;
                
                // Cập nhật vị trí
                drone = getDroneById(droneId); // Re-fetch để tránh stale data
                drone.setCurrentLatitude(newLat);
                drone.setCurrentLongitude(newLng);
                droneRepository.save(drone);
                
                System.out.println("🚁 Drone " + droneId + " di chuyển: " + 
                    String.format("%.6f, %.6f", newLat, newLng) + 
                    " (" + (i*100/steps) + "% hoàn thành)");
                
                // Ngủ trước bước tiếp theo
                if(i < steps) {
                    Thread.sleep(delayMs);
                }
            }
            
            System.out.println("✅ Drone " + droneId + " đã đến đích!");
            
        } catch(InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("❌ Animation bị gián đoạn: " + e.getMessage());
        } catch(Exception e) {
            System.err.println("❌ Lỗi animation: " + e.getMessage());
        }
    }

    // Xóa drone
    public void deleteDrone(String id) {
        droneRepository.deleteById(id);
    }
}
