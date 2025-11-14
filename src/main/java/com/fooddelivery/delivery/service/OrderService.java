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

 //  Tạo đơn hàng
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
     * Cập nhật trạng thái đơn hàng với validation
     * Luồng: PENDING → CONFIRMED → DELIVERING → COMPLETED
     * Cho phép hủy (CANCELED) khi: PENDING hoặc DELIVERING
     * PENDING → CONFIRMED yêu cầu Payment đã COMPLETED
     */
    public Order updateStatus(Long orderId, OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng cần cập nhật!"));
        
        OrderStatus currentStatus = order.getStatus();
        
        // ✅ Kiểm tra đặc biệt: PENDING → CONFIRMED cần Payment COMPLETED
        if (currentStatus == OrderStatus.PENDING && newStatus == OrderStatus.CONFIRMED) {
            boolean hasCompletedPayment = paymentRepository.existsByOrderIdAndStatus(
                orderId, 
                com.fooddelivery.delivery.entity.Payment.PaymentStatus.COMPLETED
            );
            
            if (!hasCompletedPayment) {
                throw new RuntimeException(
                    "Không thể xác nhận đơn hàng! Khách hàng chưa thanh toán (Payment status phải là COMPLETED)."
                );
            }
        }
        
        // Kiểm tra xem có thể chuyển sang trạng thái mới không
        if (!order.canTransitionTo(newStatus)) {
            String errorMessage;
            
            // Tạo message lỗi chi tiết theo từng trường hợp
            if (newStatus == OrderStatus.CANCELED) {
                if (currentStatus == OrderStatus.CONFIRMED) {
                    errorMessage = "Không thể hủy đơn hàng đã xác nhận! Nhà hàng đang chuẩn bị món ăn.";
                } else if (currentStatus == OrderStatus.COMPLETED) {
                    errorMessage = "Không thể hủy đơn hàng đã hoàn thành!";
                } else if (currentStatus == OrderStatus.CANCELED) {
                    errorMessage = "Đơn hàng đã bị hủy rồi!";
                } else {
                    errorMessage = "Không thể hủy đơn hàng ở trạng thái: " + currentStatus;
                }
            } else if (currentStatus == OrderStatus.COMPLETED) {
                errorMessage = "Đơn hàng đã hoàn thành, không thể chuyển sang trạng thái khác!";
            } else if (currentStatus == OrderStatus.CANCELED) {
                errorMessage = "Đơn hàng đã bị hủy, không thể chuyển sang trạng thái khác!";
            } else if (currentStatus == OrderStatus.PENDING && newStatus == OrderStatus.DELIVERING) {
                errorMessage = "Không thể chuyển từ PENDING sang DELIVERING! Phải xác nhận đơn (CONFIRMED) trước.";
            } else if (currentStatus == OrderStatus.PENDING && newStatus == OrderStatus.COMPLETED) {
                errorMessage = "Không thể hoàn thành đơn hàng chưa xác nhận! Luồng: PENDING → CONFIRMED → DELIVERING → COMPLETED.";
            } else if (currentStatus == OrderStatus.CONFIRMED && newStatus == OrderStatus.COMPLETED) {
                errorMessage = "Không thể hoàn thành đơn hàng chưa giao! Phải chuyển sang DELIVERING trước.";
            } else {
                errorMessage = String.format(
                    "Không thể chuyển trạng thái từ '%s' sang '%s'.",
                    currentStatus, newStatus
                );
            }
            
            throw new RuntimeException(errorMessage);
        }
        
        order.setStatus(newStatus);
        return orderRepository.save(order);
    }

    //  Xóa đơn
    public void deleteOrder(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new RuntimeException("Không thể xóa — đơn hàng không tồn tại!");
        }
        orderRepository.deleteById(orderId);
    }
}
