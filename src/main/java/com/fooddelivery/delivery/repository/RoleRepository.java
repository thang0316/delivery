package com.fooddelivery.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fooddelivery.delivery.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	
}
