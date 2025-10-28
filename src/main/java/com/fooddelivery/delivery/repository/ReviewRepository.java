package com.fooddelivery.delivery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fooddelivery.delivery.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {
	List<Review> findByUserId(String userId);
    List<Review> findByOrderRestaurantId(String restaurantId);
}
