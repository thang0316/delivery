package com.fooddelivery.delivery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import com.fooddelivery.delivery.dto.request.*;
import com.fooddelivery.delivery.entity.*;
import com.fooddelivery.delivery.repository.*;

@Service
public class ReviewService {
	@Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    // Tạo mới review
    public Review createReview(ReviewRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Review review = new Review();
        review.setUser(user);
        review.setOrder(order);
        review.setRating(request.getRating());
        review.setComment(request.getComment());

        return reviewRepository.save(review);
    }

    // Lấy danh sách tất cả review
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    // Lấy review theo ID
    public Review getReviewById(String id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
    }

    // Lấy review của 1 user
    public List<Review> getReviewsByUser(String userId) {
        return reviewRepository.findByUserId(userId);
    }

    // Lấy review của 1 nhà hàng
    public List<Review> getReviewsByRestaurant(String restaurantId) {
        return reviewRepository.findByOrderRestaurantId(restaurantId);
    }

    // Cập nhật review
    public Review updateReview(String id, ReviewRequest request) {
        Review review = getReviewById(id);

        if (request.getRating() != null) {
            review.setRating(request.getRating());
        }
        if (request.getComment() != null) {
            review.setComment(request.getComment());
        }

        return reviewRepository.save(review);
    }

    // Xóa review
    public void deleteReview(String id) {
        reviewRepository.deleteById(id);
    }
}
