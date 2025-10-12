package com.fooddelivery.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fooddelivery.delivery.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

}
