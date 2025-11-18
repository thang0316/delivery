package com.fooddelivery.delivery.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fooddelivery.delivery.dto.request.OrderRequest;
import com.fooddelivery.delivery.entity.MenuItem;
import com.fooddelivery.delivery.entity.Order;
import com.fooddelivery.delivery.entity.Order.OrderStatus;
import com.fooddelivery.delivery.entity.OrderItem;
import com.fooddelivery.delivery.entity.Restaurant;
import com.fooddelivery.delivery.entity.User;
import com.fooddelivery.delivery.repository.*;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private com.fooddelivery.delivery.repository.ReviewRepository reviewRepository; //  Tạo đơn hàng
    	public Order createOrder(OrderRequest request) {
        User customer = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng!"));

        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhà hàng!"));

        Order order = new Order();
        order.setCustomer(customer);
        order.setRestaurant(restaurant);
        order.setCustomerName(request.getCustomerName());
        order.setCustomerPhone(request.getCustomerPhone());
        order.setCustomerAddress(request.getCustomerAddress());
        order.setDeliveryLatitude(request.getDeliveryLatitude());
        order.setDeliveryLongitude(request.getDeliveryLongitude());
        order.setStatus(OrderStatus.PENDING); // Mặc định chưa xác nhận

        double total = 0.0;

        List<OrderItem> items = request.getItems().stream().map(i -> {
            MenuItem menuItem = menuItemRepository.findById(i.getMenuItemId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy món ăn trong menu!"));

         //  Kiểm tra món ăn có thuộc cùng nhà hàng với đơn hàng không
            if (!menuItem.getRestaurant().getId().equals(restaurant.getId())) {
                throw new RuntimeException("Tất cả món phải thuộc cùng 1 nhà hàng!");
            }
            
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setMenuItem(menuItem);
            orderItem.setQuantity(i.getQuantity());
            orderItem.setPrice(menuItem.getPrice());
            return orderItem;
        }).collect(Collectors.toList());

        for (OrderItem item : items) {
            total += item.getPrice() * item.getQuantity();
        }

        order.setItems(items);
        order.setTotalAmount(total);

        return orderRepository.save(order);
    }

    //  Danh sách tất cả đơn hàng
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    //  Lấy đơn hàng theo ID
    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng!"));
    }

    //  Lấy đơn theo người dùng
    public List<Order> getOrdersByCustomer(String userId) {
        return orderRepository.findByCustomer_Id(userId);
    }

    // lấy đơn hàng theo nhà hàng
    public long countOrdersByRestaurant(String restaurantId) {
        return orderRepository.countByRestaurant_Id(restaurantId);
    }

    /**
     * Cập nhật trạng thái đơn hàng
     */
    public Order updateStatus(Long orderId, OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng cần cập nhật!"));
        
        order.setStatus(newStatus);
        return orderRepository.save(order);
    }

    //  Xóa đơn
    public void deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Không thể xóa — đơn hàng không tồn tại!"));
        
        // Xóa tất cả Review trước (vì có FK tới Order)
        reviewRepository.findByOrderId(orderId).ifPresent(review -> {
            reviewRepository.delete(review);
        });
        
        // Xóa tất cả Payment (vì có FK tới Order)
        paymentRepository.findByOrderId(orderId).forEach(payment -> {
            paymentRepository.delete(payment);
        });
        
        // Xóa tất cả OrderItems
        if (order.getItems() != null && !order.getItems().isEmpty()) {
            order.getItems().clear();
            orderRepository.save(order);
        }
        
        // Xóa Order
        orderRepository.delete(order);
    }
}
