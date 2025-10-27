package com.fooddelivery.delivery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fooddelivery.delivery.entity.MenuItem;
import com.fooddelivery.delivery.entity.Order;
import com.fooddelivery.delivery.entity.OrderItem;
import com.fooddelivery.delivery.repository.*;


@Service
public class OrderItemService {

	@Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    //  Thêm món ăn vào đơn hàng
    public OrderItem addOrderItem(Long orderId, Long menuItemId, int quantity) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng ID: " + orderId));

        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy món ăn ID: " + menuItemId));

        OrderItem item = new OrderItem();
        item.setOrder(order);
        item.setMenuItem(menuItem);
        item.setQuantity(quantity);
        item.setPrice(menuItem.getPrice() * quantity);

        return orderItemRepository.save(item);
    }

    //  Lấy tất cả các món trong đơn hàng
    public List<OrderItem> getItemsByOrder(Long orderId) {
        return orderItemRepository.findByOrder_Id(orderId);
    }

    //  Lấy chi tiết 1 OrderItem
    public OrderItem getOrderItemById(Long id) {
        return orderItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy OrderItem ID: " + id));
    }

    //  Cập nhật số lượng món
    public OrderItem updateQuantity(Long id, int newQuantity) {
        OrderItem item = getOrderItemById(id);
        item.setQuantity(newQuantity);
        item.setPrice(item.getMenuItem().getPrice() * newQuantity);
        return orderItemRepository.save(item);
    }

    //  Xóa món khỏi đơn hàng
    public void deleteOrderItem(Long id) {
        if (!orderItemRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy OrderItem để xóa");
        }
        orderItemRepository.deleteById(id);
    }
	
}
