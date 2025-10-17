package com.fooddelivery.delivery.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fooddelivery.delivery.entity.Drone;
import com.fooddelivery.delivery.repository.DroneRepository;

@Service
public class DroneService {

    @Autowired
    private DroneRepository droneRepository;

    public Drone createDrone(Drone request) {
        Drone drone = new Drone();
        drone.setModel(request.getModel());
        drone.setStatus(request.getStatus());
        return droneRepository.save(drone);
    }

    public List<Drone> getDrones() {
        return droneRepository.findAll();
    }

    public Drone getDrone(String id) {
        return droneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Drone not found"));
    }

    public Drone updateDrone(String id, Drone request) {
        Drone existing = getDrone(id);
        existing.setModel(request.getModel());
        existing.setStatus(request.getStatus());
        return droneRepository.save(existing);
    }

    public void deleteDrone(String id) {
        droneRepository.deleteById(id);
    }
}
