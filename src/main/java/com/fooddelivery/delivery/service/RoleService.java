package com.fooddelivery.delivery.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fooddelivery.delivery.entity.Role;
import com.fooddelivery.delivery.repository.RoleRepository;

@Service
public class RoleService {
	private RoleRepository roleRepository;
	
	public List<Role> getAllRoles() {
        return roleRepository.findAll();
    } 
	
	public Role getRoleById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));
    }
	
}
