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

    @Autowired
    private RestaurantRepository restaurantRepository;

    // Tạo mới review
    public Review createReview(ReviewRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Kiểm tra đơn hàng đã được đánh giá chưa
        if(reviewRepository.existsByOrderId(request.getOrderId())) {
            throw new RuntimeException("Đơn hàng này đã được đánh giá rồi!");
        }

        Review review = new Review();
        review.setUser(user);
        review.setOrder(order);
        review.setRating(request.getRating());
        review.setComment(request.getComment());

        Review savedReview = reviewRepository.save(review);
        
        // Cập nhật rating trung bình của nhà hàng
        updateRestaurantRating(order.getRestaurant().getId());
        
        return savedReview;
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
        String restaurantId = review.getOrder().getRestaurant().getId();

        if (request.getRating() != null) {
            review.setRating(request.getRating());
        }
        if (request.getComment() != null) {
            review.setComment(request.getComment());
        }

        Review updatedReview = reviewRepository.save(review);
        
        // Cập nhật rating trung bình
        updateRestaurantRating(restaurantId);
        
        return updatedReview;
    }

    // Xóa review
    public void deleteReview(String id) {
        Review review = getReviewById(id);
        String restaurantId = review.getOrder().getRestaurant().getId();
        reviewRepository.deleteById(id);
        
        // Cập nhật rating trung bình
        updateRestaurantRating(restaurantId);
    }
    
    // Kiểm tra order đã có review chưa
    public boolean hasReview(Long orderId) {
        return reviewRepository.existsByOrderId(orderId);
    }
    
    // Lấy review của 1 order
    public Review getReviewByOrder(Long orderId) {
        return reviewRepository.findByOrderId(orderId).orElse(null);
    }
    
    // Tính rating trung bình cho nhà hàng
    private void updateRestaurantRating(String restaurantId) {
        List<Review> reviews = reviewRepository.findByOrderRestaurantId(restaurantId);
        
        if(reviews.isEmpty()) {
            // Nếu không có review, set mặc định 5.0
            Restaurant restaurant = restaurantRepository.findById(restaurantId).orElse(null);
            if(restaurant != null) {
                restaurant.setRating(5.0);
                restaurantRepository.save(restaurant);
            }
            return;
        }
        
        // Tính trung bình
        Double avgRating = reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(5.0);
        
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElse(null);
        if(restaurant != null) {
            restaurant.setRating(avgRating);
            restaurantRepository.save(restaurant);
        }
    }
}
