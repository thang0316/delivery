package com.fooddelivery.delivery.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fooddelivery.delivery.dto.request.DroneRequest;
import com.fooddelivery.delivery.entity.Drone;
import com.fooddelivery.delivery.service.DroneService;

@RestController
@RequestMapping("/api/drones")
public class DroneController {

	@Autowired
    private DroneService droneService;

    // 🟢 Tạo drone mới
    @PostMapping
    public ResponseEntity<Drone> createDrone(@RequestBody DroneRequest request) {
        Drone created = droneService.createDrone(request);
        return ResponseEntity.ok(created);
    }

    // 🟢 Lấy tất cả drone
    @GetMapping
    public ResponseEntity<List<Drone>> getAllDrones() {
        List<Drone> drones = droneService.getAllDrones();
        return ResponseEntity.ok(drones);
    }

    // 🟢 Lấy drone theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Drone> getDroneById(@PathVariable String id) {
        Drone drone = droneService.getDroneById(id);
        return ResponseEntity.ok(drone);
    }

    // 🟢 Lấy danh sách drone theo nhà hàng
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Drone>> getDronesByRestaurant(@PathVariable String restaurantId) {
        List<Drone> drones = droneService.getDronesByRestaurant(restaurantId);
        return ResponseEntity.ok(drones);
    }

    // 🟡 Cập nhật drone
    @PutMapping("/{id}")
    public ResponseEntity<Drone> updateDrone(
            @PathVariable String id,
            @RequestBody DroneRequest request) {
        Drone updated = droneService.updateDrone(id, request);
        return ResponseEntity.ok(updated);
    }

    // 🔴 Xóa drone
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDrone(@PathVariable String id) {
        droneService.deleteDrone(id);
        return ResponseEntity.ok("Xóa drone thành công với ID: " + id);
    }
}
