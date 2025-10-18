package com.fooddelivery.delivery.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.fooddelivery.delivery.entity.Drone;
import com.fooddelivery.delivery.service.DroneService;

@RestController
@RequestMapping("/api/drones")
public class DroneController {

    @Autowired
    private DroneService droneService;

    @PostMapping
    public Drone createDrone(@RequestBody Drone request) {
        return droneService.createDrone(request);
    }

    @GetMapping
    public List<Drone> getDrones() {
        return droneService.getDrones();
    }

    @GetMapping("/{id}")
    public Drone getDrone(@PathVariable String id) {
        return droneService.getDrone(id);
    }

    @PutMapping("/{id}")
    public Drone updateDrone(@PathVariable String id, @RequestBody Drone request) {
        return droneService.updateDrone(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteDrone(@PathVariable String id) {
        droneService.deleteDrone(id);
    }
}
