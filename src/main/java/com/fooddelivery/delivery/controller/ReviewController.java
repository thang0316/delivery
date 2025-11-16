package com.fooddelivery.delivery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.fooddelivery.delivery.dto.request.ReviewRequest;
import com.fooddelivery.delivery.entity.*;
import com.fooddelivery.delivery.service.ReviewService;


@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
	@Autowired
    private ReviewService reviewService;

    // Tạo review mới
    @PostMapping
    public Review createReview(@RequestBody ReviewRequest request) {
        return reviewService.createReview(request);
    }

    // Lấy tất cả review
    @GetMapping
    public List<Review> getAllReviews() {
        return reviewService.getAllReviews();
    }

    // Lấy review theo ID
    @GetMapping("/{id}")
    public Review getReviewById(@PathVariable String id) {
        return reviewService.getReviewById(id);
    }

    // Lấy review của 1 user
    @GetMapping("/user/{userId}")
    public List<Review> getReviewsByUser(@PathVariable String userId) {
        return reviewService.getReviewsByUser(userId);
    }

    // Lấy review của 1 nhà hàng
    @GetMapping("/restaurant/{restaurantId}")
    public List<Review> getReviewsByRestaurant(@PathVariable String restaurantId) {
        return reviewService.getReviewsByRestaurant(restaurantId);
    }

    // Kiểm tra order đã có review chưa
    @GetMapping("/check-order/{orderId}")
    public boolean hasReview(@PathVariable Long orderId) {
        return reviewService.hasReview(orderId);
    }

    // Lấy review của 1 order
    @GetMapping("/order/{orderId}")
    public Review getReviewByOrder(@PathVariable Long orderId) {
        return reviewService.getReviewByOrder(orderId);
    }

    // Cập nhật review
    @PutMapping("/{id}")
    public Review updateReview(@PathVariable String id, @RequestBody ReviewRequest request) {
        return reviewService.updateReview(id, request);
    }

    // Xóa review
    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable String id) {
        reviewService.deleteReview(id);
    }
}
