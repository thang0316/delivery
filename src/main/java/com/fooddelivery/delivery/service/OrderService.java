package com.fooddelivery.delivery.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fooddelivery.delivery.dto.request.OrderRequest;
import com.fooddelivery.delivery.entity.MenuItem;
import com.fooddelivery.delivery.entity.Order;
import com.fooddelivery.delivery.entity.OrderItem;
import com.fooddelivery.delivery.entity.Restaurant;
import com.fooddelivery.delivery.entity.User;
import com.fooddelivery.delivery.repository.MenuItemRepository;
import com.fooddelivery.delivery.repository.OrderRepository;
import com.fooddelivery.delivery.repository.RestaurantRepository;
import com.fooddelivery.delivery.repository.UserRepository;

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
        order.setStatus("ĐANG CHỜ XÁC NHẬN");

        double total = 0.0;

        List<OrderItem> items = request.getItems().stream().map(i -> {
            MenuItem menuItem = menuItemRepository.findById(i.getMenuItemId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy món ăn trong menu!"));

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

    //  Cập nhật trạng thái đơn hàng
    public Order updateStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng cần cập nhật!"));
        order.setStatus(status);
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
